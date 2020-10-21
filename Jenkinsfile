def call() {
    pipeline {
        agent { node { label 'docker' } }

        stages {
            stage('Build') {
                agent {
                    dockerfile {
                        args "--env-file ${WORKSPACE}/.env.docker"
                    }
                }
                stages {
                    stage("Step 1") {
                        steps {
                            sh 'echo hello world'
                        }
                        post {
                            always {
                                sh 'ruby --version'
                            }
                        }
                    }
                    stage("Step 2") {
                        steps {
                            sh 'echo hello world2'
                            sh '''ruby -e "puts ENV.fetch('NAME1')"'''
                        }
                    }
                }
            }
            stage('Build 2') {
                steps {
                    sh 'echo $NAME3'
                }
            }
            stage('Publish') {
                steps {
                    script {
                        def image = docker.build("jenkins-test:${GIT_COMMIT}")
                    }
                }
            }
        }
        post {
            always {
                sh 'echo hello'
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
