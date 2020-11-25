// pipeline {
//     agent { docker { image 'ruby' } }
//     stages {
//         stage('build') {
//             steps {
//                 sh 'ruby --version'
//             }
//         }
//     }
// }
pipeline {
    agent any
    parameters {
        string(name: "repo_name")
        string(name: "git_sha")
        string(name: "status")
    }
    stages {
        stage('Build') {
            steps {
                githubNotify description: "This commit is being built",
                             status: "PENDING",
                             sha: params.git_sha,
                             repo: params.repo_name,
                             context: "jenkins/common-integration"
                sh 'echo "Helloooo World"'
                sh "echo 'the git sha is ${params.git_sha}'"
                sh 'git rev-parse HEAD'
                sh 'sleep 30'
                sh '''
                    echo "Multiline shell steps works too"
                    ls -lah
                '''
            }
        }
    }
    post {
        always {
            githubNotify description: currentBuild.currentResult,
                         status: currentBuild.currentResult,
                         sha: params.git_sha,
                         repo: params.repo_name,
                         context: "jenkins/common-integration"
        }
    }
}
