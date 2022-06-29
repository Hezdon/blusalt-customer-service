FROM adoptopenjdk:11-jdk-hotspot

#COPY target/lib lib

COPY target/customer-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 5050

ARG JAR_FILE

ENV JAVA_OPTS=""

EVN SPRING_PROFILE = "application.properties"

ENTRYPOINT exec java $JAVA_OPTS \
 -Dspring.profiles.active=$SPRING_PROFILE \
 -jar app.jar