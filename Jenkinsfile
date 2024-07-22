pipeline {
  agent {
    any {
      image 'Dockerfile'
      args '-v /var/run/docker.sock:/var/run/docker.sock'
    }
  }
  stages {
    stage ('git clone') {
      steps {
        git(url: 'https://github.com/boxfuse/boxfuse-sample-java-war-hello.git', branch: 'master', poll: true)
      }
    }
    stage ('Build image') {
      steps {
        sh 'docker build -t .'
      }
    }
  } 
} 