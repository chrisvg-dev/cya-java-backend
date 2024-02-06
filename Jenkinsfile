pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'cvillegas92/ms-cristhianvg-projects-cya'
        APP_NAME = 'ms-cristhianvg-projects-cya'
        APP_PORT = '9501'
        SUBDOMAIN = 'cya-postulation-project'
    }

    stages {
        stage('Maven Test') {
            steps {
                withMaven(maven: 'maven') {
                    sh 'mvn test'
                }
            }
        }

        stage('Maven Build') {
            steps {
                withMaven(maven: 'maven') {
                    sh 'mvn clean package -DskipTests'
                }

            }
        }

        stage('Docker build') {
            steps {
                sh "docker build -t ${APP_NAME} ."
            }
        }

        stage('Docker push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh "docker push ${DOCKER_IMAGE}"
                }
            }
        }

        stage('Deploy') {
            steps {
                sh """
                    #!/bin/bash
                    if [ -n "$(docker ps -f \"name=${DOCKER_IMAGE}\" -f \"status=running\" -q )" ]; then
                        echo "The container is running!"
                        docker stop ${DOCKER_IMAGE}
                        docker rm ${DOCKER_IMAGE}
                    fi
                    docker compose -f spring.docker.yml up -d
                """
            }
        }


    }
}