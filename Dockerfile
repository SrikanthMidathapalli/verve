# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the host to the container
COPY target/verve-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8085

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
