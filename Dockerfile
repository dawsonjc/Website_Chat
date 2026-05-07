FROM gradle:8.2.1-jdk11 as build

ENV GRADLE_VERSION=8.2.1
ENV GRADLE_HOME=/opt/gradle
ENV PATH=${GRADLE_HOME}/bin:${PATH}

RUN apt-get -y update && apt-get install -y && \
    apt-get install -y vim

RUN gradle --version

WORKDIR ./app

COPY . .
COPY ./backend ./backend
COPY ./scala_js ./scala_js
COPY ./frontend ./frontend

CMD [ "gradle", ":frontend:bootRun", "--scan", "--stacktrace" ]

