# Multi-stage build: build with Maven, run with a lightweight JRE
# Build stage
FROM maven:3.9.4-eclipse-temurin-17 as build
WORKDIR /workspace

# Copy only pom first to leverage Docker layer cache for dependencies
COPY pom.xml .
# If you have a parent pom or modules, copy them as needed here

# Download dependencies (will be cached while pom.xml doesn't change)
RUN mvn -B -DskipTests dependency:go-offline

# Copy the rest of the sources and build
COPY src ./src
RUN mvn -B -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
ARG JAR_NAME=notification-service-0.0.1-SNAPSHOT.jar

# Copy the fat jar (adjust path/name if your artifactId/version differ)
COPY --from=build /workspace/target/${JAR_NAME} /app/app.jar

EXPOSE 8080

# Use a non-root user (optional)
RUN addgroup --system app && adduser --system --ingroup app app
USER app

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
