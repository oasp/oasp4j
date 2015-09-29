@echo off
call %~dp0\runbatch.bat billExportJob

if errorlevel 0 goto success
echo Failed.
goto end
:success
echo Success.
:end