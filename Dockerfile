FROM gradle:8.1-jdk17-alpine AS cache
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./
RUN gradle dependencies --no-daemon

FROM gradle:8.1-jdk17-alpine AS build
WORKDIR /app
COPY --from=cache /app/build.gradle.kts .
COPY --from=cache /app/settings.gradle.kts .
COPY --from=cache /root/.gradle /root/.gradle
COPY src/  ./src/
RUN gradle clean build -x test --no-daemon

FROM gradle:8.1-jdk17-alpine
WORKDIR /app
COPY --from=build /app/build/libs/backend-0.0.1-SNAPSHOT.jar ./app.jar
CMD ["java", "-jar", "app.jar"]
