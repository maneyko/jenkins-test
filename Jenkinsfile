pipeline {
    agent any
    stages {
        stage('Build') {
            agent { docker { image 'ruby:3.0-rc' } }
            steps {
                sh 'echo "Hello World"'
                sh '''
                    echo "Multiline shell steps works too"
                    ls -lah
                    pwd
                '''
                sh 'pwd'
                sh '''
                    ruby << EOT
                        def square(x) = x * x

                        square(9) => n

                        puts n
                    EOT
                '''
            }
        }
    }
}
