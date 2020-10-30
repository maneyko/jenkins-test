def call(projectName = "none", boolVar = false) {
    pipeline {
        agent { node { label "docker" } }
        stages {
            stage('Build') {
                agent {
                    dockerfile {
                        args """
                          -e ENVVAR1=var1 \
                          -e ENVVAR2=var2 \
                          -e ENVVAR3=var3 \
                          -e ENVVAR4=var4
                        """
                    }
                }
                stages {
                    stage('Tests') {
                        steps {
                            sh 'echo "Hello World"'
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
    }
}

def func() {
    sh 'echo "this is func()"'
}
