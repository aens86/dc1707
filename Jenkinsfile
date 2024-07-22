pipeline {
  agent {
    docker {
       args '-v /var/run/docker.sock:/var/run/docker.sock'
    }
  }
  tools {
    dockerTool "my_docker"
   
  }

  stages {
    stage ('git') {
      steps {
         git(url: 'https://github.com/aens86/dc1707', branch: 'main', poll: true)
      }
    }
    stage ('Build Image') {
      steps {        
        sh 'cd /var/jenkins_home/workspace/dc1707'
        sh 'ls'
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
        sh '''ssh root@10.129.0.33 << EOF
	         docker pull 10.129.0.5:8123/war:1.0
	         docker-compose up -d '''
      }
    }
  }
}