FROM openjdk:19
VOLUME /var
COPY build/libs/*.jar app.jar
EXPOSE 8282
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]