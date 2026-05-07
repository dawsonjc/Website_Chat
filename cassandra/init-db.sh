#!/bin/bash

# Wait for Cassandra to start up
echo "Waiting for Cassandra to start..."
sleep 30

# Run the CQL script using cqlsh
echo "Running CQL script..."

# Assuming the CQL script is copied to /docker-entrypoint-initdb.d/init-db.cql
cqlsh -f /docker-entrypoint-initdb.d/project.cql

echo "CQL script executed successfully!"