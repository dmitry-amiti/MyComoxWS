#
# Build stage
#
FROM maven:3.3.9-jdk-8 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:8-jdk-slim
COPY --from=build /target/mycomox-publisher-1.0.jar demo.jar
# ENV PORT=8080
EXPOSE 8080
# ENTRYPOINT ["java","-jar","demo.jar"]
ENTRYPOINT ["java", "-Xms64m", "-Xmx512m","-jar","demo.jar"]
