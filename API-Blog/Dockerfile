# Phase 1
FROM openjdk:11 as build
RUN mkdir /home/sblog
WORKDIR /home/sblog

COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .

RUN chmod +x ./mvnw

RUN ./mvnw dependency:go-offline -B

COPY . .

RUN ./mvnw clean package

# Phase 2
FROM openjdk:11-jre as production

ARG HOME=/home/sblog

WORKDIR /home/sblog

COPY --from=build ${HOME}/src/main/resources src/main/resources

COPY --from=build ${HOME}/target target

#RUN ls -la ./target
RUN ["chmod", "-R", "777", "/home/sblog"]
#RUN ls -la ./target

ENTRYPOINT ["java", "-jar", "target/API-Blog-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT /bin/bash echo Hello