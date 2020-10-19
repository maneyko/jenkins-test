pipeline {
    node { label 'docker' }
    environment {
        NAME = sh(script: "echo ${GIT_COMMIT}", , returnStdout: true).trim()
        SPACE = sh(script: "echo ${WORKSPACE}", , returnStdout: true).trim()
    }
    stages {
        stage('Build') {
            steps {
                script {
                    loadEnvironmentVariablesFromFile("${WORKSPACE}/.env.docker")
                }
                sh 'echo "Hello World"'
                sh 'echo $NAME > commit.txt'
                sh 'cat commit.txt'
                sh 'echo $SPACE > space.txt'
                sh 'cat space.txt'
                sh 'echo $NAME3 > name3.txt'
                sh 'cat name3.txt'
                sh 'echo hello > hello.txt'
                sh '''
                    echo "Multiline shell steps works too"
                    ls -lah
                    pwd
                '''
                sh 'pwd'
                sh '''ruby -e "def square(x) = x * x; square(9) => n; puts ENV['NAME2']"'''
            }
        }
        stage("Stage 2") {
            stages {
                stage("Step 1") {
                    steps {
                        sh "echo 'Hello!'"
                        sh 'cat hello.txt'
                    }
                }
                stage("Step 2") {
                    steps {
                        sh "echo 'Hello 2!'"
                    }
                }
                stage("Step 3") {
                    script {
                        node(null) {
                            sh 'echo hello'
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            sh 'echo "Hello post"'
        }
    }
}

private void loadEnvironmentVariablesFromFile(String path) {
    def file = readFile(path)
    file.split('\n').each { envLine ->
        def (key, value) = envLine.tokenize('=')
        env."${key}" = "${value}"
    }
}
