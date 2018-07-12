#!/bin/bash
javac esi18/*/*.java
javac -cp ".:lib/*" DavyJonesLocker.java
java -cp ".:lib/*" DavyJonesLocker
echo "Generating Replay URL:"
curl --silent --data "@results.json" https://mimirbattleships.glitch.me/data --header "Content-Type: application/json"
mv results.json files/results.json
echo ""