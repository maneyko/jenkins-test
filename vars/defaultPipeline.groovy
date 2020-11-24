class MyClass {
    String  var1  = "var1"
    String  var2  = "var2"
    String  var3  = "var3"
    String  var4  = "var4"
    Boolean bool1 = true
}

def call(projectName = "none", boolVar = false) {
    opts = new MyClass(var1: "var111")
    pipeline {
        environment {
            GIT_COMMIT_SHORT = "${GIT_COMMIT.take(7)}"
        }
        agent { node { label "docker" } }
        stages {
            stage('Build') {
                agent {
                    dockerfile {
                        args """
                          -e ENVVAR1=var1 \
                          -e ENVVAR2=var2 \
                          -e ENVVAR3=var3 \
                          -e ENVVAR4=var4
                        """
                    }
                }
                stages {
                    stage('parallel stages') {
                        parallel {
                            stage("step 1") {
                                steps {
                                    sh 'echo step 1'
                                    sh "echo ${opts.var1}"
                                    sh "echo ${GIT_COMMIT}"
                                    sh "echo ${GIT_COMMIT_SHORT}"
                                }
                            }
                            stage("step 2") {
                                steps {
                                    sh 'echo step 2'
                                }
                            }
                            stage("Tests") {
                                when { expression { projectName && true } }
                                steps {
                                    sh 'echo "Hello World"'
                                    func()
                                    sh "echo 'The project name is ${projectName}'"
                                    sh '''
                                      if test -n "$ENVVAR5"; then
                                        echo 1
                                      fi
                                    '''
                                    sh '''
                                      echo var1 $ENVVAR1
                                      echo var2 $ENVVAR2
                                      echo var3 $ENVVAR3
                                      echo var4 $ENVVAR4
                                    '''
                                    sampleMethod(projectName)
                                    sh '''
                                        echo "Multiline shell steps works too"
                                        ls -lah
                                    '''
                                    sh """
                                        if test -n '${boolVar ? "true" : ""}'; then
                                          echo 'was true'
                                        else
                                          echo 'was false'
                                        fi
                                    """
                                    sh 'echo hello!'
                                    build job: 'maneyko - GitHub/jenkins-test/master-proj'
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

def func() {
    sh 'echo "this is func()"'
}

def makeOpts(Map map = [:]) {
    new MyClass(map.subMap(
            MyClass.getDeclaredFields().grep { !it.synthetic }.collect { key -> key.name }
    ))
}
