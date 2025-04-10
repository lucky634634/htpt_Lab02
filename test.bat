@echo off

setlocal enabledelayedexpansion
cmd /c .\compile.bat

start cmd /c .\run.bat
start cmd /c .\run.bat 