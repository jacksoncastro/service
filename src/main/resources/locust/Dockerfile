FROM grubykarol/locust

LABEL maintainer="jack.vasc@yahoo.com.br"

COPY config /locust

ENV ATTACKED_HOST http://localhost:8080/api

ENV LOCUST_OPTS "-c 100 -r 10"