FROM openjdk:8-jre-alpine

LABEL maintainer="jack.vasc@yahoo.com.br"

VOLUME /tmp

ARG DEPENDENCY=target/dependency

COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib

COPY ${DEPENDENCY}/META-INF /app/META-INF

COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT [ "java", "-cp","app:app/lib/*", "br.com.jackson.ServiceApplication" ]