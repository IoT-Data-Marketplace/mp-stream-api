FROM maven:3.6.3-jdk-11  as imageBuilder
WORKDIR /workspace/app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:11.0.7-jre-slim
WORKDIR /workspace/app
COPY --from=imageBuilder /workspace/app/target/mpstreamapi-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "mpstreamapi-0.0.1-SNAPSHOT.jar"]