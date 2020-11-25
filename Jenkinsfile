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
                sh 'echo "Helloooo World"'
                sh "echo 'the git sha is ${params.git_sha}'"
                sh 'sleep 30'
                sh '''
                    echo "Multiline shell steps works too"
                    ls -lah
                '''
                githubNotify description: "worked!",
                             status: params.status,
                             sha: params.git_sha,
                             repo: params.repo_name,
                             context: "jenkins/common-integration"
            }
        }
    }
}
