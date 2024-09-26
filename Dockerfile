FROM amazoncorretto:17

ENV spring.datasource.initialization-mode=always
ENV SPRING_PROFILES_ACTIVE=prod

COPY ./build/libs/GooimanServer-0.0.1-SNAPSHOT.jar /opt/application.jar

WORKDIR /opt/

CMD ["java", "-jar", "/opt/application.jar"]