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
                sh '''
                    echo "Multiline shell steps works too"
                    ls -lah
                    pwd
                '''
                sh 'pwd'
                sh 'ruby -e "def square(x) = x * x; square(9) => n; puts n"'
            }
            stages {
                stage("Step 1") {
                    steps {
                        sh "echo 'Hello!'"
                    }
                }
                stage("Step 2") {
                    steps {
                        sh "echo 'Hello 2!'"
                    }
                }
            }
        }
    }
}
