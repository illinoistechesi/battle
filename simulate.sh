#!/bin/bash
javac -cp ".:lib/*" DavyJonesLocker.java
java -cp ".:lib/*" DavyJonesLocker
echo "Generating Replay URL:"
curl --silent --data "@results.json" https://mimirbattleships.glitch.me/data --header "Content-Type: application/json"
echo ""