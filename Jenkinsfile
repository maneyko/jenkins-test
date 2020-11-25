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
                githubNotify description: "worked!",
                             status: "SUCCESS",
                             sha: params.git_sha,
                             repo: params.repo_name,
                             context: "continuous-integration/jenkins3"
            }
        }
    }
}
