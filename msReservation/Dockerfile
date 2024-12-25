FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ./target/msReservation-0.0.1-SNAPSHOT.jar service_reserv.jar
ENTRYPOINT ["java", "-jar","/service_reserv.jar"]
