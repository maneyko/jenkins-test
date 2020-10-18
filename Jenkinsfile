pipeline {
    agent none
    stages {
        stage('Build') {
            agent {
                dockerfile {
                    reuseNode true
                }
            }
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
        }
    }
}
