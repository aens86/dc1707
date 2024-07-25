pipeline {
  agent {
    dockerfile true
  }

  stages {

    stage('Copy source') {
      steps {
        git(url: 'https://github.com/aens86/demo3', branch: 'master', poll: true,)
      }
    }

    stage('Build') {
      steps {
        sh 'mvn package'
      }
    }

    stage('Make docker image') {
      steps {
        sh 'cp -R /var/jenkins_home/workspace/pls/webapp/target/*.war ./ && docker build -t 1807 .'
        sh 'docker login -u admin -p 123 10.129.0.5:8123'

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