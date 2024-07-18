pipeline {
  agent any

  stages {
    stage ('git') {
      steps {
        git 'https://github.com/aens86/homework.git'

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
        sh 'ssh-keychan'
      }
    }
  }
}

    
// This step should not normally be used in your script. Consult the inline help for details.
withDockerRegistry(credentialsId: '864a14eb-4c02-447c-a691-d3aa8137bca0', url: '10.129.0.5:8123') {
    // some block
}
  }

  stages {

    stage('Copy source with configs') {
      steps {
        git(url: 'https://github.com/aens86/homework.git', branch: 'main', poll: true )
        sh 'ssh-keyscan -H devbuild-srv01 >> ~/.ssh/known_hosts'
        sh 'scp jenkins@devbuild-srv01:/home/jenkins/build/configs/staging/gateway-api/application-business-config-defaults.yml gateway-api/src/main/resources/application-business-config-defaults.yml'
      }
    }

    stage('Make docker image') {
      steps {
        sh 'cp -R gateway-api/build/libs/* docker-setup/shop/gateway-api && cd docker-setup/shop/gateway-api && docker build --tag=gateway-api .'
        sh '''docker tag gateway-api devcvs-srv01:5000/shop2-backend/gateway-api:2-staging && docker push devcvs-srv01:5000/shop2-backend/gateway-api:2-staging'''

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

errr