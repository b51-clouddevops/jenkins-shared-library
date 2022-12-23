def call() {

properties([
  parameters([
     choice(name: 'ENV', choices: ['dev', 'prod'],  description: 'Chose the env to deploy')
     choice(name: 'ACTION', choices: ['apply', 'destroy'],  description: 'Chose the action')
    
  ])
])

node {
    ansiColor('xterm') {
        git branch: 'main', url: "https://github.com/b51-clouddevops/${REPONAME}.git"

        stage('Terraform Init') {

                sh ''' 
                    
                '''

            }
        }
    }
}

// pipeline {
//     agent any 
//     parameters {
//         choice(name: 'ENV', choices: ['dev', 'prod'], description: 'Select The Environment')
//         choice(name: 'ACTION', choices: ['apply', 'destroy'], description: 'Select Apply or Destroy')
//     }
//     options {
//         ansiColor('xterm')    // Add's color to the output : Ensure you install AnsiColor Plugin.
//     }
//     stages {
//         stage('Terraform Init') {
//             steps {
//                 sh "terrafile -f env-${ENV}/Terrafile"
//                 sh "terraform init --backend-config=env-${ENV}/${ENV}-backend.tfvars"
//             }
//         }

//         stage('Terraform Plan') {
//             steps {
//                 sh "terraform plan -var-file=env-${ENV}/${ENV}.tfvars"
//             }
//         }

//         stage('Terraform Apply') {
//             steps {
//                 sh "terraform ${ACTION} -var-file=env-${ENV}/${ENV}.tfvars -auto-approve "
//             }
//         }

//     }
// }