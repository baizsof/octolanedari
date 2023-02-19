FROM openjdk:8-jdk-slim

COPY . .

CMD ./gradlew test && ./gradlew run