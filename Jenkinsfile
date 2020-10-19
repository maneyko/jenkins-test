pipeline {
    agent { node { label 'docker' } }
    environment {
        NAME = sh(script: "echo ${GIT_COMMIT}", , returnStdout: true).trim()
        SPACE = sh(script: "echo ${WORKSPACE}", , returnStdout: true).trim()
    }
    stages {
        stage('Build') {
            agent { Eval.me("defaultAgent()") }
            steps {
                sh 'echo "Hello World"'
                sh 'echo $NAME > commit.txt'
                sh 'cat commit.txt'
                sh 'echo $SPACE > space.txt'
                sh 'cat space.txt'
                sh 'echo $NAME3 > name3.txt'
                sh 'cat name3.txt'
                sh 'echo hello > hello.txt'
                sh '''
                    echo "Multiline shell steps works too"
                    ls -lah
                    pwd
                '''
                sh 'pwd'
                sh '''ruby -e "def square(x) = x * x; square(9) => n; puts ENV['NAME2']"'''
            }
        }
        stage("Stage 2") {
            stages {
                stage("Step 1") {
                    agent { Eval.me("defaultAgent()") }
                    steps {
                        sh "echo 'Hello!'"
                        sh 'echo $NAME2'
                        sh 'cat hello.txt'
                    }
                }
                stage("Step 2") {
                    steps {
                        sh "echo 'Hello 2!'"
                    }
                }
                stage("Step 3") {
                    steps {
                        script {
                            docker.image('ruby:2.5').inside {
                                sh 'ruby --version'
                            }
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            sh 'echo "Hello post"'
        }
    }
}

private void defaultAgent() {
    dockerfile {
        args "--env-file ${WORKSPACE}/.env.docker"
        reuseNode true
    }
}
