pipeline {
  agent {
    dockerfile true
  }
  stages {
    stage ('Build image') {
      steps {
        sh 'docker build -t test:1.0 /var/jenkins_home/workspace'
      }
    }
  } 
} 