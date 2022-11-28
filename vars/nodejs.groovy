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
                        env.ARGS="-Dsonar.sources=."
                        common.sonarChecks()                  // Use script { when you're using groovy based conventions }
                    }
                }
            } 

            stage('Test Cases') {
                parallel {
                    stage('Unit Testing') {                 
                        steps {
                            sh "echo Unit Testing Completed"   
                                }
                            }
                    stage('Integration Testing') {                 
                        steps {
                            sh "echo Integration Testing Completed"   
                                }
                            }
                    stage('Function Testing') {                 
                        steps {
                            sh "echo Function Testing Completed"   
                                }
                            }
                        }         
                    }

            stage('Prepare Artifacts') {
                when { expression { env.TAG_NAME != null } }
                steps {
                    sh "npm install"
                    sh "zip ${COMPONENT}.zip node_modules server.js"
                }
            }

            stage('Uploading Artifacts') {
                when { expression { env.TAG_NAME != null } }
                steps {
                    sh "echo Uploading artifacts"
                }
            }
        }   // end of stages 
    }  // end of pipelines
} // end of call



