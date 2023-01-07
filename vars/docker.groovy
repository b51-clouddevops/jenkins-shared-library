def call() {
    node {
        sh "rm -rf *"
        git branch: 'main', url: "https://github.com/b51-clouddevops/${COMPONENT}.git"
        
            stage('Docker Build') {
                sh "docker build -t 834725375088.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:latest ."
             }
    
        if(env.TAG_NAME != null ) {
            stage('Docker Push') {
                sh """
                   docker tag 834725375088.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:latest 834725375088.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME}
                   aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 834725375088.dkr.ecr.us-east-1.amazonaws.com
                   docker push 834725375088.dkr.ecr.us-east-1.amazonaws.com/frontend:${TAG_NAME}
                   """
           }
        }
    }
}