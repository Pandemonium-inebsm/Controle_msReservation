FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ./target/auth-service-0.0.1-SNAPSHOT.jar auth_service.jar
ENTRYPOINT ["java", "-jar","/auth_service.jar"]
