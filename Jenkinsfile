node {
    checkout scm

    def customImage = docker.build("jenkins-test:${env.BUILD_ID}")

    customImage.inside("--env-file ${WORKSPACE}/.env.docker") {
        stage('Stage 1') {
            sh 'ruby --versiion'
            sh 'echo $NAME3'
        }
    }
    sh "echo 'Hello ${currentBuild.currentResult}'"
}
