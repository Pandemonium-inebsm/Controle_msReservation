FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ./target/msSalle-0.0.1-SNAPSHOT.jar service_salle.jar
ENTRYPOINT ["java", "-jar","/service_salle.jar"]
