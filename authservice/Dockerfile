# Stage 1: Build stage
FROM public.ecr.aws/docker/library/maven:3.9.7-amazoncorretto-21 AS build

# Install commons into local Maven repo
COPY commons/pom.xml commons/pom.xml
COPY commons/src commons/src
RUN mvn -f commons/pom.xml install -DskipTests

# Copy Maven files for dependency resolution
COPY authservice/pom.xml ./
COPY authservice/src src

# Build the project and create the executable JAR
RUN mvn clean install -DskipTests -Dmaven.test.skip=true

# Stage 2: Run stage
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21

# Set working directory
WORKDIR authservice

# Copy the JAR file from the build stage
COPY --from=build target/*.jar authservice.jar

# Expose port 1112
EXPOSE 1112

# Set the entrypoint command for running the application
ENTRYPOINT ["java", "-jar", "authservice.jar"]
