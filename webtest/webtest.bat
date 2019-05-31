@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK18~1.0_1

REM ============= RECREATING DATABASE =============
cd "c:\Program Files\MySQL\MySQL Server 5.7\bin"
set SQL_SCRIPT=c:\0_workspace\KP_Spring01\src\sql\dropDB.txt
mysql --user=root --password=mikimiki --verbose --execute="\. %SQL_SCRIPT%"

set SQL_SCRIPT=c:\0_workspace\KP_Spring01\src\sql\createDB.txt
mysql --user=root --password=mikimiki --verbose --execute="\. %SQL_SCRIPT%"

set SQL_SCRIPT=c:\0_workspace\KP_Spring01\src\sql\initDB.txt
mysql --user=kp --password=mikimiki --verbose --execute="\. %SQL_SCRIPT%" kp_spring01

set SQL_SCRIPT=c:\0_workspace\KP_Spring01\src\sql\populateDB.txt
mysql --user=kp --password=mikimiki --verbose --execute="\. %SQL_SCRIPT%" kp_spring01

cd "c:\0_workspace\KP_Spring01\webtest"
call c:\tools\Canoo-WebTest-3.0\bin\webtest.bat -buildfile build.xml
pause
