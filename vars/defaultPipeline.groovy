def call(projectName = "none", boolVar = false) {
    opts = new MyClass(var1: "var111")
    myfunc = opts.myfunc
    pipeline {
        environment {
            GIT_COMMIT_SHORT = "${GIT_COMMIT.take(7)}"
        }
        agent { node { label "docker" } }
        stages {
            stage('Build') {
                stages {
                    stage('parallel stages') {
                        parallel {
                            stage("step 1") {
                                steps {
                                    sh 'echo step 1'
                                    sh "echo ${opts.var1}"
                                    sh "echo ${GIT_COMMIT}"
                                    sh "git branch"
                                    sh "git rev-parse --short HEAD"
                                    sh "echo changeid: ${env.CHANGE_ID}"
                                    sh "echo ${GIT_COMMIT_SHORT}"
                                }
                            }
                            stage("step 1.5") {
                                steps {
                                    myfunc(this)
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
                                }
                            }
                        }
                    }
                    stage("other build") {
                        steps {
                            script {
                                GIT_SHA = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
                            }
                            build job: 'maneyko - GitHub/jenkins-test/test-master',
                                  parameters: [
                                    string(name: "repo_name", value: "jenkins-test"),
                                    string(name: "git_sha",   value: "$GIT_SHA"),
                                    string(name: "status",    value: currentBuild.currentResult)
                                  ],
                                  propagate: true,
                                  wait: false
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

class MyClass {
    String  var1  = "var1"
    String  var2  = "var2"
    String  var3  = "var3"
    String  var4  = "var4"
    Boolean bool1 = true
    def myfunc = { script ->
        script.sh "echo 'var1 is ${var1}'"
        script.sh "echo 'bool1 is ${bool1}'"
    }
}

def makeOpts(Map map = [:]) {
    new MyClass(map.subMap(
            MyClass.getDeclaredFields().grep { !it.synthetic }.collect { key -> key.name }
    ))
}
