# Stage 1: Build stage
FROM public.ecr.aws/docker/library/maven:3.9.7-amazoncorretto-21 AS build

COPY pom.xml ./
COPY src src

RUN mvn clean install -DskipTests -Dmaven.test.skip=true

# Stage 2: Run stage
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21

WORKDIR signalingservice

COPY --from=build target/*.jar signalingservice.jar

EXPOSE 1122

ENTRYPOINT ["java", "-jar", "signalingservice.jar"]
