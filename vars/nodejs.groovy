def lintChecks() {
        sh "echo installing jslinst"
        sh "npm i jslint"   
        sh "node_modules/jslint/bin/jslint.js server.js || true"
        sh "echo Lint Checks Completed for $COMPONENT"
}


// function call will be called by default, when you call the fileName
def call() {
    pipeline{
        agent any
        environment {
            SONAR    = credentials('SONAR')
            SONARURL = "172.31.0.59"
        } 
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

            stage('XYZ Checks') {
                steps {
                    sh "echo Performing XYZ Checks"
                    sh "echo this takes 30 mins"
                }
            } 
        }   // end of stages 
    }  // end of pipelines
} // end of call



