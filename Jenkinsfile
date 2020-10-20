def call() {
    pipeline {
        agent { node { label 'docker' } }

        stages {
            stage('Build') {
                agent {
                    dockerfile {
                        args "--env-file ${WORKSPACE}/.env.docker"
                        reuseNode true
                    }
                }
                stages {
                    stage("Step 1") {
                        steps {
                            sh 'echo hello world'
                        }
                    }
                    stage("Step 2") {
                        steps {
                            sh 'echo hello world2'
                            sh '''ruby -e "puts ENV.fetch('NAMEE1')"'''
                        }
                    }
                }
            }
            stage('Build 2') {
                steps {
                    sh 'echo $NAME3'
                }
            }
        }
        stage('Publish') {
            steps {
                script {
                    def image = docker.build("jenkins-test:${GIT_COMMIT}")
                }
            }
        }
        post {
            always {
                def image = docker.build("jenkins-test:${GIT_COMMIT}")
            }
        }
    }
}

call()

// Load ENV variables dynamically, ideally this file would be parsed
// in the `agent` block at the top-level, but ${WORKSPACE} is not yet defined.
private void loadEnvironmentVariablesFromFile(String path) {
    def file = readFile(path)
    file.split('\n').each { envLine ->
        def (key, value) = envLine.tokenize('=')
        env."${key}" = "${value}"
    }
}
