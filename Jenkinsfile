pipeline {
    agent any
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
                sh 'docker build -t cvillegas92/ms-cristhianvg-projects-cya .'
            }
        }

        stage('Docker push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                    sh 'docker push cvillegas92/ms-cristhianvg-projects-cya'
                }
            }
        }

        stage('Deploy') {
            steps {
                sh '''#!/bin/bash
                    if [ -n "$(docker ps -f "name=cvillegas92/ms-cristhianvg-projects-cya" -f "status=running" -q )" ]; then
                        echo "The container is running!"
                        docker stop cvillegas92/ms-cristhianvg-projects-cya
                        docker rm cvillegas92/ms-cristhianvg-projects-cya
                    fi
                    docker compose -f spring.docker.yml up -d
                '''
            }
        }

        stage('Update Nginx') {
            steps {
                sh '''#!/bin/bash
                    sudo cp /opt/utils/template /opt/utils/ms-cristhianvg-projects-cya
                    sudo sed -i 's/server_name rpg.cristhianvg.dev/server_name cya-postulation-project.cristhianvg.dev/g' /opt/utils/ms-cristhianvg-projects-cya
                    sudo sed -i 's|proxy_pass http://localhost:9191;|proxy_pass 9501;|g' /opt/utils/ms-cristhianvg-projects-cya
                    sudo ufw allow 9501
                    sudo ufw reload
                    sudo nginx -t
                    sudo systemctl restart nginx
                '''
            }
        }
    }
}