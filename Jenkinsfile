node {
    def WORKSPACE = "/var/lib/jenkins/workspace/chatGPT-deploy"
    def dockerImageTag = "chatGPT-deploy${env.BUILD_NUMBER}"

    try{
         stage('Clone Repo') {
            // from a GitHub repository
            git url: 'https://github.com/bilal-maida/ChatGpt-AI.git',
                credentialsId: 'bilal',
                branch: 'main'
         }
          stage('Build docker') {
                 dockerImage = docker.build("chatGPT-deploy:${env.BUILD_NUMBER}")
          }

          stage('Deploy docker'){
                  echo "Docker Image Tag Name: ${dockerImageTag}"
                  sh "docker stop chatGPT-deploy || true && docker rm chatGPT-deploy || true"
                  sh "docker run --name chatGPT-deploy -d -p 8090:8090 chatGPT-deploy:${env.BUILD_NUMBER}"
          }
    }catch(e){
        throw e
    }finally{
    }
}
