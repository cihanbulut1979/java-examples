FROM adoptopenjdk/openjdk11:jdk-11.0.2.9-slim
ENV PORT 9191
COPY target/*.jar /opt/app.jar
WORKDIR /opt
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar --server.port=9191