# syntax=docker/dockerfile:1
FROM amazoncorretto:17

ARG SPRING_PROFILES_ACTIVE=prod

ENV spring.datasource.initialization-mode=always
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
COPY ./build/libs/gooiman-server-0.0.1-SNAPSHOT.jar /opt/application.jar

WORKDIR /opt/

CMD ["java", "-jar", "/opt/application.jar"]