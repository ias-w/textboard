##### MAVEN BUILD STAGE #####
FROM docker.io/library/maven:3-eclipse-temurin-17 as build
WORKDIR /work
COPY pom.xml .
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn clean install

##### IMAGE BUILD STAGE #####
FROM docker.io/bellsoft/liberica-runtime-container:jre-17-glibc
WORKDIR /work
COPY --from=build /work/target/*.jar /work/application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./application.jar"]
