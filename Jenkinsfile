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
                sh """
                curl -XPOST \
                  -H "Accept: application/vnd.github.v3+json" \
                  https://$JENKINS_GITHUB_PSW:x-oauth-basic@api.github.com/repos/maneyko/jenkins-test/statuses/${params.git_sha} \
                  -d'{"state":"success", "target_url":"https://example.com/build/status", "description":"build worked!!!", "context":"continuous-integration/jenkins2"}'
                """
            }
        }
    }
}
