@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-12
set TOMCAT_HOME=c:\tools\apache-tomcat-9.0.20

del /F /Q ..\logs\*
del /F /Q %TOMCAT_HOME%\logs\*.log
rmdir /S /Q %TOMCAT_HOME%\work\Catalina\localhost\kp_spring01
pushd %cd%
cd %TOMCAT_HOME%\bin
call startup.bat
popd