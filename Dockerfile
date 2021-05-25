FROM maven:3-openjdk-11-slim as build

COPY . ./

RUN mvn package

FROM adoptopenjdk/openjdk11-openj9:alpine-jre as runtime
COPY --from=build target/desafio-backend-wine-1.0.0.jar .

ARG DB_HOST_ARG
ARG DB_NAME_ARG
ARG DB_USERNAME_ARG
ARG DB_PASSWORD_ARG

ENV DB_HOST=${DB_HOST_ARG} \
    DB_NAME=${DB_NAME_ARG} \
    DB_USERNAME=${DB_USERNAME_ARG} \
    DB_PASSWORD=${DB_PASSWORD_ARG}

EXPOSE 8080

CMD ["java", "-jar", "desafio-backend-wine-1.0.0.jar"]