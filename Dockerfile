FROM luizhcanassa/ubuntu-openjdk-21:latest AS build

COPY . .

RUN ./gradlew build -x test --info

FROM luizhcanassa/ubuntu-openjdk-21:latest
LABEL authors="luiz-henrique-canassa"

EXPOSE 8080

COPY --from=build /build/libs/*.jar app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]