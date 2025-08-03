# Stage 1: Build the Spring Boot JAR
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copy source code
COPY . .

# Build the application (skip tests for speed)
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the Spring Boot JAR
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
