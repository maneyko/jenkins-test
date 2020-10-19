def call(env_file) {
    pipeline {
        agent {
            dockerfile {
                args "--env-file ${env_file}"
                reuseNode true
            }
        }

        stages {
            stage('Build') {
                sh 'ruby --version'
            }
            stage('Step 1') {
                sh 'echo $NAME3'
            }
        }
        post {
            always {
                sh "echo 'Hello post'"
            }
        }
    }
}

call("${WORKSPACE}/.env.docker")
