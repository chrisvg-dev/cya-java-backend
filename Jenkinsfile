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
    }
}