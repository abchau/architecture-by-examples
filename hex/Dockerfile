FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/hex-0.0.1-SNAPSHOT.jar hex.jar
ENTRYPOINT ["java", "-jar", "/app/hex.jar"]
EXPOSE 8080