# ==============================
# Build Stage
# ==============================
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Copy entire multi-module project
COPY . .

# Make Gradle executable
RUN chmod +x gradlew

# Build ONLY poi service
RUN ./gradlew :travel-poi-service:bootJar --no-daemon

# ==============================
# Runtime Stage
# ==============================
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy built jar from module
COPY --from=builder /app/travel-poi-service/build/libs/*.jar app.jar

# JVM optimization for containers
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

EXPOSE 8082

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]