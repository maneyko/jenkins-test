def call(projectName = "none", boolVar = true) {
    pipeline {
        agent any
        stages {
            stage('Build') {
                when { boolVar }
                steps {
                    sh 'echo "Hello World"'
                    sh "echo 'The project name is ${projectName}'"
                    sampleMethod(projectName)
                    sh '''
                        echo "Multiline shell steps works too"
                        ls -lah
                    '''
                    func()
                }
                when { ! boolVar }
                steps {
                    sh 'echo "boolVar" is falsey!'
                    func()
                }
            }
            post {
                always {
                    sh 'echo post'
                }
            }
        }
    }
}

def func() {
    sh 'echo "this is func()"'
}
