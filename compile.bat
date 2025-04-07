@echo off

mkdir build
mkdir bin
mkdir log

echo Main-Class: App > build/manifest.MF

javac -cp lib/* -d bin src/*.java && jar cvfm build/App.jar build/manifest.MF -C bin .
