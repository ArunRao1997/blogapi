#!/usr/bin/env sh

# Build the project
./gradlew bootJar

## Build the docker file
#docker build -t raonayineni/blog-api

# Run via docker compose
docker-compose up --build --force-recreate -d
