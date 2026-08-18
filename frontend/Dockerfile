# syntax=docker/dockerfile:1
# Build with the repository root as the context:
# docker build -f frontend/Dockerfile .

FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /workspace

COPY gradlew gradlew.bat settings.gradle build.gradle ./
COPY gradle ./gradle
COPY backend ./backend
COPY frontend ./frontend
COPY scala_js ./scala_js
COPY auth_service ./auth_service

RUN chmod +x gradlew \
    && ./gradlew :frontend:bootWar --no-daemon \
    && cp frontend/build/libs/frontend-1.0-SNAPSHOT.war /workspace/app.war

FROM eclipse-temurin:21-jre-jammy AS runtime

WORKDIR /app

COPY --from=build /workspace/app.war ./app.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.war"]
