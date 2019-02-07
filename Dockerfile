FROM openjdk:8-jdk-alpine

COPY build/libs/resolver-1.0.war resolver.war

EXPOSE 8888

CMD java $JAVA_OPTS -jar resolver.war