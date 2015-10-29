@echo off
call %~dp0\runbatch.bat billExportJob bills.file=%~dp0\tmp\bills.csv

if errorlevel 0 goto success
echo Failed.
goto end
:success
echo Success.
:end