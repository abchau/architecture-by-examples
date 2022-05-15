FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/ddd-0.0.1-SNAPSHOT.jar ddd.jar
ENTRYPOINT ["java", "-jar", "/app/ddd.jar"]
EXPOSE 8080