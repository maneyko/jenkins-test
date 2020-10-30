def call(projectName = "none", boolVar = false) {
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
                    sh """
                        if [[ -n '${boolVar ? "true" : ""}' ]]; then
                          echo 'was true'
                        else
                          echo 'was false'
                        fi
                    """
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
