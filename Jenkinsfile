def call() {
    node {
        checkout scm

        try {
            def customImage = docker.build("jenkins-test:${env.BUILD_ID}")

            customImage.inside("--env-file ${WORKSPACE}/.env.docker") {
                stage('Stage 1') {
                    sh 'ruby --versiion'
                    sh 'echo $NAME3'
                }
            }
        } catch(e) {
            currentBuild.result = 'FAILED'
            throw e
        } finally {
            sh "echo 'Hello ${currentBuild.currentResult}'"
            sh 'echo hi'
            sh """echo 'Changes URL: ${env.RUN_CHANGES_DISPLAY_URL}'"""
            sh 'echo hi2'
        }
    }
}

call()
