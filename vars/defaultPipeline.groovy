class MyClass implements Serializable {
    String  var1  = "var1"
    String  var2  = "var2"
    String  var3  = "var3"
    String  var4  = "var4"
    Boolean bool1 = true
    List theSteps = [
        "echo 'script class is none'",
        "echo 'the var1 is ${var1}'"
    ]

    def myfunc2(script) {
        theSteps.each { theStep ->
            script.sh theStep
        }
    }
}

def func(script) {
    script.sh 'echo "this is func()"'
    script.sh "echo 'script class is ${script.class}'"
    script.sh 'echo "this is the second line of func()"'
}

def call(projectName = "none", boolVar = false) {
    opts = new MyClass(var1: "var111")


    pipeline {
        environment {
            ABC = "${getAbc()}"
            GIT_COMMIT_SHORT = "${GIT_COMMIT.take(7)}"
            FULL_PATH_BRANCH = "${sh(script: 'git name-rev --name-only HEAD', returnStdout: true)}".trim()
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
                                    sh "echo 'git url is: ${env.GIT_URL}.'"
                                    sh "echo 'git change_id is: ${env.CHANGE_ID}.'"
                                    sh "git branch"
                                    sh "git rev-parse --short HEAD"
                                    sh "echo git branch is: ${env.GIT_BRANCH}"
                                    sh "echo change branch is: ${env.CHANGE_BRANCH}"
                                    sh "echo change ID is: ${env.CHANGE_ID}"
                                    sh "echo full path branch is: ${env.FULL_PATH_BRANCH}"
                                    sh "echo 'ABC is: ${ABC}'"
                                    sh "git --no-pager show -s --format='%an'"
                                    sh "git log -n 1 --pretty=format:'%s%n%n%b'"
                                    sh "echo ${GIT_COMMIT_SHORT}"
                                }
                            }
                            stage("step 1.5") {
                                steps {
                                    script {
                                        gitBranch = sh(script: 'git name-rev --name-only HEAD', returnStdout: true).trim()
                                        repoUrl   = sh(script: 'git remote show origin -n', returnStdout: true).trim()

                                        sh "echo 'the branch name is ${gitBranch}. repo url is ${repoUrl}.'"
                                        opts.myfunc2(this)

                                        if (env.CHANGE_ID) {
                                            sh "echo changeid is: ${env.CHANGE_ID}"
                                            sh "echo pull request is ${pullRequest.head}"
                                            pullRequest.files.each { thefile ->
                                                sh "echo the file is '${thefile.getFilename()}'"
                                            }
                                        }
                                    }
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
                                    // myfunc(this)
                                    func(this)
                                    sh "echo 'The project name ${GIT_BRANCH} is ${projectName}'"
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
                            sh "sleep 15"
                            script {
                                GIT_SHA = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
                            }
                            build job: 'maneyko - GitHub/jenkins-test/test-master',
                                  parameters: [
                                    string(name: "repo_name", value: "jenkins-test"),
                                    string(name: "git_sha",   value: "$GIT_SHA"),
                                    string(name: "undefParameter", value: "$GIT_SHA"),
                                    string(name: "status",    value: currentBuild.currentResult)
                                  ],
                                  propagate: true,
                                  wait: false
                        }
                    }
                }
            }
        }
        post {
            always {
                sh "echo 'build time: ${currentBuild.durationString.replace(" and counting", "")}'"
            }
        }
    }
}

def getAbc() {
    if (["aaa", "bbb", "ccc"].contains("bbb")) {
        return "123"
    } else {
        return "456"
    }
}

def makeOpts(Map map = [:]) {
    new MyClass(map.subMap(
            MyClass.getDeclaredFields().grep { !it.synthetic }.collect { key -> key.name }
    ))
}
