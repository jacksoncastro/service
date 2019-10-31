FROM grubykarol/locust

LABEL maintainer="jack.vasc@yahoo.com.br"

COPY config /locust

ENV ATTACKED_HOST http://speedup.local.com

ENV LOCUST_OPTS "-c 100 -r 10"