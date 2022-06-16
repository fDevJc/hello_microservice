#!/bin/sh
./gradlew clean && ./gradlew build && java -jar ./build/libs/discovery-service-0.0.1-SNAPSHOT.jar
