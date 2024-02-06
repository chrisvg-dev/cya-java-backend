FROM openjdk:17-jdk-slim
ADD target/cristhianvg.jar /opt/apps/cvillegas/cristhianvg.jar
WORKDIR /opt/apps/cvillegas
ENTRYPOINT ["java","-jar","cristhianvg.jar"]