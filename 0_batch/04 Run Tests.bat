@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK18~1.0_1
set ANT_HOME=c:\tools\apache-ant-1.10.1
set BUILDFILE=c:\0_workspace\KP_Spring01\build.xml
set TARGETS=clean tests

del /F /Q ..\logs\test.log
call %ANT_HOME%\bin\ant.bat -buildfile %BUILDFILE% %TARGETS%
pause