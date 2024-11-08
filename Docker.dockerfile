FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

RUN echo "start running wallet-service app"

ENTRYPOINT ["java","-jar","app.jar"]