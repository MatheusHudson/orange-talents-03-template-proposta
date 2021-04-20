    FROM openjdk:11
    MAINTAINER Matheus
    ARG JAR_FILE=target/*.jar
    COPY ${JAR_FILE} /proposta.jar
    ENTRYPOINT ["java","-jar","/proposta.jar"]


