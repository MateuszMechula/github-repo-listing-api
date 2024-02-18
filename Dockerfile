FROM openjdk:21
WORKDIR /app
COPY build/libs/github.jar app.jar
CMD ["java", "-jar", "app.jar"]