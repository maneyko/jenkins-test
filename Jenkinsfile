pipeline {
    agent { docker { image 'ruby:3.0-rc' } }
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
                sh 'ruby --version'
            }
        }
    }
}
