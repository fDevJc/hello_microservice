#!/bin/sh
./gradlew clean && ./gradlew build && java -jar ./build/libs/apigateway-service-0.0.1-SNAPSHOT.jar
