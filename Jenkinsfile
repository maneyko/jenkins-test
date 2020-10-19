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
                steps {
                    sh 'ruby --version'
                }
            }
            stage('Step 1') {
                agent { dockerfile true }
                steps {
                    sh 'echo $NAME3'
                    sh '''ruby -e "puts ENV['NAME1']"'''
                }
            }
        }
        post {
            always {
                sh "echo 'Hello post'"
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
