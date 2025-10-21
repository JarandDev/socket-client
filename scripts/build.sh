#!/bin/bash

APP=socket-client
TAG=192.168.0.201:5000/$APP:local

mvn clean package

docker buildx build --platform=linux/amd64,linux/arm64 -t $TAG .
