FROM maven:3.8.3-openjdk-17 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:17-jdk
MAINTAINER arobles
COPY --from=build /usr/src/app/target/usergenerator-0.0.1-SNAPSHOT.jar /usr/app/usergenerator-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/usr/app/usergenerator-0.0.1-SNAPSHOT.jar"]