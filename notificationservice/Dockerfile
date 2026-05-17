# Stage 1: Build stage
FROM maven:3.9.7-amazoncorretto-21 AS build

COPY pom.xml ./
COPY src src

RUN mvn clean install -DskipTests

# Stage 2: Run stage
FROM amazoncorretto:21

WORKDIR notificationservice

COPY --from=build target/*.jar notificationservice.jar

EXPOSE 1130

ENTRYPOINT ["java", "-jar", "notificationservice.jar"]
