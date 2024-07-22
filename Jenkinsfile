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
        git(url: 'https://github.com/aens86/dc1707', branch: 'master', poll: true)
        sh 'pwd'
      }
    }
    stage ('Build image') {
      steps {
        sh 'docker build -t test:1.0 .'
      }
    }
  } 
} 