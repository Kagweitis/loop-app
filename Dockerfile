# Copy the entire project (excluding files specified in .dockerignore)
COPY . .

# Build the application
RUN mvn clean install -DskipTests

# Use the official OpenJDK image as the base image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the container
COPY --from=build /app/target/LoopDFSCardAccount-0.0.1-SNAPSHOT.jar .

# Expose the port that your Spring Boot application will run on
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "LoopDFSCardAccount-0.0.1-SNAPSHOT.jar"]