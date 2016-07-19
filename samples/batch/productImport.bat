@echo off
call %~dp0\runbatch.bat classpath:config/app/batch/beans-productimport.xml productImportJob drinks.file=file:src/test/resources/drinks.csv meals.file=file:src/test/resources/meals.csv date(date)=2015/12/20
