# syntax=docker/dockerfile:1
FROM amazoncorretto:17

ARG SPRING_PROFILES_ACTIVE=prod
ARG DATABASE_ADDRESS=db.gooiman.internal
ARG DATABASE_USERNAME=root
ARG DATABASE_PASSWORD=password

ENV spring.datasource.initialization-mode=always
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
ENV DATABASE_ADDRESS=${DATABASE_ADDRESS}
ENV DATABASE_USERNAME=${DATABASE_USERNAME}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}
COPY ./build/libs/gooiman-server-0.0.1-SNAPSHOT.jar /opt/application.jar

WORKDIR /opt/

CMD ["java", "-jar", "/opt/application.jar"]