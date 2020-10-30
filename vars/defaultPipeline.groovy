def call(projectName = "none", boolVar = true) {
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    sh 'echo "Hello World"'
                    func()
                    sh "echo 'The project name is ${projectName}'"
                    sampleMethod(projectName)
                    sh '''
                        echo "Multiline shell steps works too"
                        ls -lah
                    '''
                    sh 'true && exit 1'
                    sh 'echo hello!'
                }
                post {
                    always {
                        sh 'echo post'
                    }
                }
            }
        }
    }
}

def func() {
    sh 'echo "this is func()"'
}
