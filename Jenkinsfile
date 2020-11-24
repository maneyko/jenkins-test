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
            string(name: "test_param1", defaultValue: "test_param1_default_value")
        }
    stages {
        stage('Build') {
            steps {
                sh 'echo "Helloooo World"'
                sh "echo 'test_param1 is: ${params.test_param1}'"
                sh '''
                    echo "Multiline shell steps works too"
                    ls -lah
                '''
            }
        }
    }
}
