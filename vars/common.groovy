def sonarChecks() {
        sh "echo Starting Code Quality Analysis"
        // sh "sonar-scanner -Dsonar.host.url=http://172.31.0.59:9000 -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=${COMPONENT} ${ARGS}"
        // sh "bash -x sonar-quality-gate.sh ${SONAR_USR} ${SONAR_PSW} ${SONARURL} ${COMPONENT}" 
        sh "echo "
}