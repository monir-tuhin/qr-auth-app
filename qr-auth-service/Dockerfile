# -------- Stage 1: Build the JAR --------
#FROM maven:3.8.5-openjdk-8 AS builder
## Set workdir
#WORKDIR /app
## Copy pom.xml and download dependencies first (caching)
#COPY pom.xml .
#RUN mvn dependency:go-offline -B
#
## Copy source code
#COPY src ./src
## Build the application
#RUN mvn clean package -DskipTests

# -------- Stage 2: Run the JAR --------
# Use a base image with Java 8
FROM adoptopenjdk/openjdk8
# Set the working directory
WORKDIR /app
# Copy jar from the builder stage
#COPY --from=builder /app/target/*.jar app.jar
COPY qr-auth-service.jar app.jar
# Run with prod profile
CMD ["java","-jar","app.jar","--spring.profiles.active=prod"]