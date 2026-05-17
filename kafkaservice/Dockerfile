# Stage 1: Build stage
FROM public.ecr.aws/docker/library/maven:3.9.7-amazoncorretto-21 AS build

COPY commons/pom.xml commons/pom.xml
COPY commons/src commons/src
RUN mvn -f commons/pom.xml install -DskipTests

COPY kafkaservice-client/pom.xml kafkaservice-client/pom.xml
COPY kafkaservice-client/src kafkaservice-client/src
RUN mvn -f kafkaservice-client/pom.xml install -DskipTests

COPY kafkaservice/pom.xml ./
COPY kafkaservice/src src

RUN mvn clean install -DskipTests -Dmaven.test.skip=true

# Stage 2: Run stage
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21

WORKDIR kafkaservice

COPY --from=build target/*.jar kafkaservice.jar

EXPOSE 1120

ENTRYPOINT ["java", "-jar", "kafkaservice.jar"]
