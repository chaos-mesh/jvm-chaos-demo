#!/usr/bin/env bash

mvn clean package
docker build . -t gallardot/chaosmesh-jvmchaos-sample:latest -f Dockerfile.local


