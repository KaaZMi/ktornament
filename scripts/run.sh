#!/bin/bash

cd ..
mkdir -p logs
nohup ./gradlew run > logs/back.log &
echo $! > logs/back.pid

cd front
npm run start
