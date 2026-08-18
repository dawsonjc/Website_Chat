FROM cassandra:4.1.7

# The application reads messages through a Cassandra materialized view.
RUN sed -ri 's/^materialized_views_enabled:.*/materialized_views_enabled: true/' /etc/cassandra/cassandra.yaml

EXPOSE 9042

