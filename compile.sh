#!/bin/bash

mkdir bin
mkdir build

echo Main-Class: App > build/manifest.MF

javac -d bin src/*.java && jar cvfm build/App.jar build/manifest.MF -C bin .