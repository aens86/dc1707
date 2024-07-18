pipeline {
  agent any

  stages {
    stage ('git') {
      steps {
        git 'https://github.com/aens86/dc1707.git'

      }
    }
    stage ('Build Image') {
      steps {
        sh 'docker build -t war:v1.0 .'
      }
    }
    stage ('Push registry') {
      steps {
        sh 'docker login -u admin -p 123 10.129.0.5:8123'
        sh 'docker tag war:1.0 10.129.0.5:8123/war:1.0'
        sh 'docker push 10.129.0.5:8123/war:1.0'
      }
    }
    stage ('Deploy') {
      steps {
        sh 'ssh-keyscan -H 10.129.0.33 >> ~/.ssh/known_hosts'
        sh '''ssh jenkins@devbe-srv01 << EOF
	         docker pull 10.129.0.5:8123/war:1.0
	         docker-compose up -d '''
      }
    }
  }
}