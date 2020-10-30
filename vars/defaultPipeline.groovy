def call(projectName = "none", boolVar = false) {
    pipeline {
        node { label 'docker' }
        stages {
            dockerfile {
                args """
                  -e ENVVAR1=var1 \
                  -e ENVVAR2=var2 \
                  -e ENVVAR3=var3 \
                  -e ENVVAR4=var4 \
                """
            }
            stage('Build') {
                steps {
                    sh 'echo "Hello World"'
                    func()
                    sh "echo 'The project name is ${projectName}'"
                    sh """
                      echo var1 $ENVVAR1
                      echo var2 $ENVVAR2
                      echo var3 $ENVVAR3
                      echo var4 $ENVVAR4
                    """
                    sampleMethod(projectName)
                    sh '''
                        echo "Multiline shell steps works too"
                        ls -lah
                    '''
                    sh """
                        if test -n '${boolVar ? "true" : ""}'; then
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
