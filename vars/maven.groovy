def lintChecks() {
    sh "mvn checkstyle:check || true"
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
                        sh "mvn clean compile"
                        env.ARGS="-Dsonar.java.binaries=target/"
                        common.sonarChecks()                  // Use script { when you're using groovy based conventions }
                    }
                }
            }     
            stage('Test Cases') {
                steps {
                    script {
                        sh "mvn clean compile"
            
                    }
                }
            }   
        }   // end of stages 
    }  // end of pipelines
} // end of call



