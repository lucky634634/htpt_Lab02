@echo off

setlocal enabledelayedexpansion
cmd /c .\compile.bat

start java -jar build/App.jar "a" 5000
start java -jar build/App.jar "b" localhost 5000
@REM start java -jar build/App.jar "c" localhost 5000
@REM start java -jar build/App.jar "d" localhost 5000
@REM start java -jar build/App.jar "e" localhost 5000