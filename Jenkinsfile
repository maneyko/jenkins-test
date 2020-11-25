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
    environment {
        JENKINS_GITHUB = credentials('jenkins-github')
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
                githubNotify description: "The commit (${params.git_sha}) worked!!",
                             status: "success",
                             account: 'maneyko',
                             sha: params.git_sha,
                             targetUrl: "https://example.com/build/success",
                             repo: params.repo_name
            }
        }
    }
}
