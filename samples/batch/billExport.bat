@echo off
call %~dp0\runbatch.bat classpath:config/app/batch/beans-billexport.xml billExportJob bills.file=bills.csv date(date)=2015/12/20
