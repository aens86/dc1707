pipeline {
  agent {
    dockerfile true
  }
  stages {
    stage ('git clone') {
      steps {
        git(url: 'https://github.com/aens86/dc1707', branch: 'master', poll: true)
        sh 'ls /var/jenkins_home/workspace/prod'
      }
    }
    stage ('Build image') {
      steps {
        sh 'docker build -t test:1.0 /var/jenkins_home/workspace'
      }
    }
  } 
} 