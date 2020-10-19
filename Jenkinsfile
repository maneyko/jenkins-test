node {
    checkout scm

    def customImage = docker.build("jenkins-test:${env.BUILD_ID}")

    customImage.withRun("--env-file ${WORKSPACE}/.env.docker") { c ->
        sh 'ruby --version'
        sh 'echo $NAME3'
    }
    post {
        always {
            sh 'echo "Hello post"'
        }
    }
}
