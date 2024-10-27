FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y

COPY . .

RUN ./gradlew build -x test --info

FROM luizhcanassa/ubuntu-openjdk-21:latest
LABEL authors="luiz-henrique-canassa"

EXPOSE 8080

COPY --from=build /build/libs/*.jar app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]