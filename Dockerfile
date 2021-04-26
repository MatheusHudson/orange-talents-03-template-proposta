FROM openjdk:11
COPY target/*.jar app/app.jar
ENTRYPOINT ["java","-jar","app/app.jar"]
EXPOSE 8080