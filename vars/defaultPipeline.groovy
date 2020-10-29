def call(projectName = "none") {
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    sh 'echo "Hello World"'
                    sh "echo 'The project name is ${projectName}'"
                    sh '''
                        echo "Multiline shell steps works too"
                        ls -lah
                    '''
                }
            }
        }
    }
}
