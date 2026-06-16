# Stage 1: Build stage
FROM public.ecr.aws/docker/library/maven:3.9.7-amazoncorretto-21 AS build

# Copy Maven files for dependency resolution
COPY pom.xml ./
COPY .mvn .mvn

# Copy application source code
COPY src src

# Build the project and create the executable JAR
RUN mvn clean install -DskipTests -Dmaven.test.skip=true

# Stage 2: Run stage
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21

# Set working directory
WORKDIR eurekaserver

# Copy the JAR file from the build stage
COPY --from=build target/*-exec.jar eurekaserver.jar

# Expose port 8761
EXPOSE 8761

# Set the entrypoint command for running the application
ENTRYPOINT ["java", "-jar", "eurekaserver.jar"]
