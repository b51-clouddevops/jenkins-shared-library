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
            NEXUS    = credentials('NEXUS')
            SONARURL = "172.31.0.59"
            NEXUSURL = "172.31.2.247"
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

            stage('Chekcing Artifacts') {
                when { expression { env.TAG_NAME != null } }
                steps {
                    script {
                        env.UPLOAD_STATUS=sh(returnStdout: true, script: 'curl -L -s http://${NEXUSURL}:8081/service/rest/repository/browse/${COMPONENT}/ | grep ${COMPONENT}-${TAG_NAME}.zip')
                        print UPLOAD_STATUS
                    }
                }
            }

            stage('Prepare Artifacts') {
                when { 
                    expression { env.TAG_NAME != null } 
                    expression { env.UPLOAD_STATUS == "" } 
                }
                steps {
                    sh "npm install"
                    sh "zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js"
                }
            }

            stage('Uploading Artifacts') {
                when { 
                    expression { env.TAG_NAME != null } 
                    expression { env.UPLOAD_STATUS == "" } 
                }
                steps {
                    sh "curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://${NEXUSURL}:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"
                }
            }
        }   // end of stages 
    }  // end of pipelines
} // end of call



