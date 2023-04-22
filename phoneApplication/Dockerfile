FROM dependency-cache:latest
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew && ./gradlew build
RUN ./gradlew build

EXPOSE 3000

CMD ["java", "-jar", "build/libs/phoneApplication-1.0-SNAPSHOT.jar"]