FROM openjdk:17-jdk-slim
ADD target/cya_exam.jar /opt/apps/cvillegas/cya_exam.jar
WORKDIR /opt/apps/cvillegas
ENTRYPOINT ["java","-jar","cya_exam.jar"]