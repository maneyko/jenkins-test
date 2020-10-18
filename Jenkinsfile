pipeline {
    agent {
        dockerfile {
            reuseNode true
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'echo "Hello World"'
                sh 'echo hello > hello.txt'
                sh '''
                    echo "Multiline shell steps works too"
                    ls -lah
                    pwd
                '''
                sh 'pwd'
                sh 'ruby -e "def square(x) = x * x; square(9) => n; puts n"'
            }
        }
        stage("Stage 2") {
            parallel {
                stage("Step 1") {
                    steps {
                        sh "echo 'Hello!'"
                        sh 'cat hello.txt'
                    }
                }
                stage("Step 2") {
                    steps {
                        sh "echo 'Hello 2!'"
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
}
