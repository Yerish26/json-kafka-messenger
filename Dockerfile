# Use a base image with Java runtime
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/JsonKafkaMessenger-0.0.1-SNAPSHOT.jar /app/json-kafka-messenger.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "json-kafka-messenger.jar"]
