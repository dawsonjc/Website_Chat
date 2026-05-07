FROM cassandra:3.11.16

EXPOSE 9042

COPY project.cql /docker-entrypoint-initdb.d/
COPY init-db.sh  /docker-entrypoint-initdb.d/

RUN apt-get -y update && apt-get install -y && \
    apt-get install -y vim

RUN chmod +x /docker-entrypoint-initdb.d/init-db.sh

