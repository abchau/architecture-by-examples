FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/hddd-0.0.1-SNAPSHOT.jar hddd.jar
ENTRYPOINT ["java", "-jar", "/app/hddd.jar"]
EXPOSE 8080