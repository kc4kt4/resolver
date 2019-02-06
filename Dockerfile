FROM alpine:3.8

COPY build/libs/resolver-1.0.war resolver.war

CMD java -jar resolver-1.0.war