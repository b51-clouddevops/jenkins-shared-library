def call() {
    node {
        sh "rm -rf *"
        git branch: 'main', url: "https://github.com/b51-clouddevops/${COMPONENT}.git"
        
        stage('Docker Build') {
            sh "docker build ."
        }
    }
}