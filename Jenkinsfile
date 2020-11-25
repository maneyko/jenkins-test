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
        }
    stages {
        stage('Build') {
            steps {
                sh 'echo "Helloooo World"'
                sh "echo 'the git sha is ${git_sha}'"
                sh 'sleep 120'
                sh '''
                    echo "Multiline shell steps works too"
                    ls -lah
                '''
            }
        }
        post {
            always {
                githubNotify description: "This is a shortened example",
                             status: currentBuild.currentResult,
                             sha: params.git_sha,
                             repo: params.repo_name
            }
        }
    }
}
