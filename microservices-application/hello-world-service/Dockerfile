FROM eclipse-temurin:latest
VOLUME /tmp
ARG JAR_FILE=target/hello-world-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#docker build --build-arg JAR_FILE=target/HelloWorldDocker-0.0.1-SNAPSHOT.jar -t helloworldimage .
