#!/bin/sh

podman run \
  -e "SPRING_PROFILES_ACTIVE=default" \
  -e "JAVA_OPTS=-Ddebug -Xmx128m" \
  -p 8080:8080 \
  -t localhost/3tier
spring.config.location
spring.jmx.enabled=false

-noverify
-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap