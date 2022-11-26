def lintChecks() {
    sh "echo list checks started for payment * * * * ......"
    sh "echo Lint Checks Completed for $COMPONENT"
}

def sonarChecks() {
        sh "echo Starting Code Quality Analysis"
        sh "sonar-scanner -Dsonar.host.url=http://172.31.0.59:9000 -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=${COMPONENT} -Dsonar.sources=."
        sh "bash -x sonar-quality-gate.sh ${SONAR_USR} ${SONAR_PSW} ${SONARURL} ${COMPONENT}"
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


// pylint fileName.py 


