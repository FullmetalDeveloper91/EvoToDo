FROM bellsoft/liberica-openjre-alpine:21-cds

WORKDIR /application

COPY target/*.jar app.jar

RUN java -XX:ArchiveClassesAtExit=app.jsa -Dspring.context.exit=onRefresh -jar app.jar & exit 0

ENTRYPOINT java -jar app.jar
