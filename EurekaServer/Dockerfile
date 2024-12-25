FROM eclipse-temurin:17-jre-alpine

VOLUME /tmp

ARG JAR_FILE=target/*.jar

COPY ./target/EurekaServer-0.0.1-SNAPSHOT.jar /eurika-server.jar

ENTRYPOINT ["java","-jar","/eurika-server.jar"]