# member - Dockerfile
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY build/libs/*.jar delivery.jar

ENTRYPOINT ["java", "-jar","-DEUREKA-HOST=eureka-server", "/app/delivery.jar"]