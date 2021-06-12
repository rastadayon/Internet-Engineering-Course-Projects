FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD

MAINTAINER Ghazal Kalhor & Rasta Tadayon

COPY pom.xml /build/

COPY src /build/src/

WORKDIR /build/

RUN mvn package

FROM openjdk:8-jre-alpine

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/*.war /app/app.war

ENTRYPOINT ["java", "-jar", "app.war", "sh"]

# Commands
# docker build -f DockerFile -t back .
# docker run -d --name back -p 3001:8080 back
# docker logs {someCode} -f
