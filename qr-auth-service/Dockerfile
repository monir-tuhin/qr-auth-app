# Use a base image with Java 8
FROM adoptopenjdk/openjdk8
# Set the working directory
WORKDIR /app
# Copy the pre-built JAR file from your local machine to the container
#COPY target/qr-code-generation.jar app.jar
COPY qr-code-generation.jar app.jar
CMD ["java","-jar","app.jar","--spring.profiles.active=prod"]