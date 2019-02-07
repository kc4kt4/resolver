FROM openjdk:8-jdk-alpine

COPY build/libs/resolver-1.0.war resolver.war

CMD java -Dspring.profiles.active=dev -jar resolver.war