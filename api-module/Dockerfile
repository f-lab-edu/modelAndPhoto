FROM openjdk:11-jre-slim
WORKDIR /app
COPY build/libs/api-module-0.0.1-SNAPSHOT.jar app/api.jar
ENTRYPOINT ["java", "-jar", "app/api.jar"]