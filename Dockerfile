# Use OpenJDK as base image
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the built jar file
COPY target/affirmations-*.jar app.jar

# Expose port (default Spring Boot port)
EXPOSE 8081

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]