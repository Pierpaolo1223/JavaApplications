FROM eclipse-temurin:latest
VOLUME /tmp
ARG JAR_FILE=target/SpringSecurityTest-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]