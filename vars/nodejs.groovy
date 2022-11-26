def lintChecks() {
        sh "echo installing jslinst"
        sh "npm i jslint"   
        sh "node_modules/jslint/bin/jslint.js server.js || true"
        sh "echo Lint Checks Completed for $COMPONENT"
}

def sonarChecks() {
        sh "echo Starting Code Quality Analysis"
        sh "sonar-scanner -Dsonar.host.url=http://172.31.0.59:9000 -Dsonar.projectKey=${COMPONENT} -Dsonar.sources=."
}


// function call will be called by default, when you call the fileName
def call() {
    pipeline{
        agent any 
        stages {
            stage('Lint Checks') {
                steps {
                    script {
                        lintChecks()                  // Use script { when you're using groovy based conventions }
                    }
                }
            }   

            stage('Sonar Checks') {
                steps {
                    script {
                        sonarChecks()                  // Use script { when you're using groovy based conventions }
                    }
                }
            } 
        }   // end of stages 
    }  // end of pipelines
} // end of call



