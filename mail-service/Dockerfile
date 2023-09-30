FROM openjdk:19
VOLUME /var
COPY build/libs/*.jar app.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "app.jar"]