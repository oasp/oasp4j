@echo off
call %~dp0\runbatch.bat productImportJob drinks.file=%~dp0\src\test\resources\drinks.csv meals.file=%~dp0\src\test\resources\meals.csv

if errorlevel 0 goto success
echo Failed.
goto end
:success
echo Success.
:end