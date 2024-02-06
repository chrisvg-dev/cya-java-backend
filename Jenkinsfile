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
                sh """#!/bin/bash
                    if [ -n \"$(docker ps -f \"name=${DOCKER_IMAGE}\" -f \"status=running\" -q )\" ]; then
                        echo \"The container is running!\"
                        docker stop ${DOCKER_IMAGE}
                        docker rm ${DOCKER_IMAGE}
                    fi
                    docker compose -f spring.docker.yml up -d
                """
            }
        }

        stage('Update Nginx') {
            steps {
                sh """#!/bin/bash
                    sudo cp /opt/utils/template /opt/utils/${APP_NAME}
                    sudo sed -i 's/server_name rpg.cristhianvg.dev/server_name ${SUBDOMAIN}.cristhianvg.dev/g' /opt/utils/${APP_NAME}
                    sudo sed -i 's|proxy_pass http://localhost:9191;|proxy_pass http://localhost:${APP_PORT};|g' /opt/utils/${APP_NAME}
                    sudo mv /opt/utils/${APP_NAME} /etc/nginx/sites-available/${APP_NAME}
                    sudo ln -sf /etc/nginx/sites-available/${APP_NAME} /etc/nginx/sites-enabled/${APP_NAME}
                    sudo ufw allow ${APP_PORT}
                    sudo ufw reload
                    sudo nginx -t
                    sudo systemctl restart nginx
                """
            }
        }
    }
}