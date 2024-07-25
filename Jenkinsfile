pipeline {
  agent {
    dockerfile true
  }

  stages {

    stage('Copy source') {
      steps {
        git(url: 'https://github.com/aens86/demo3', branch: 'master', poll: true,)
        sh 'ls'
      }
    }

    stage('Build') {
      steps {
        sh 'mvn package'
        sh 'ls'
      }
    }

    stage('Make docker image') {
      steps {
        sh 'cp -R /webapp/target/*.war ./ && docker build -t 1807 .'

      }
    }

    stage('Run docker on devbe-srv01') {
      steps {
        sh 'ssh-keyscan -H devbe-srv01 >> ~/.ssh/known_hosts'
        sh '''ssh jenkins@devbe-srv01 << EOF
	sudo docker pull devcvs-srv01:5000/shop2-backend/gateway-api:2-staging
	cd /etc/shop/docker
	sudo docker-compose up -d
EOF'''
      }
    }
  }
  triggers {
    pollSCM('*/1 H * * *')
  }
}