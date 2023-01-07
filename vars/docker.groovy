def call() {
    node {
        sh "rm -rf *"
        git branch: 'main', url: "https://github.com/b51-clouddevops/${COMPONENT}.git"
        
        stage('Docker Build') {
            sh "docker build -t 834725375088.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:latest"
        }
    }
}