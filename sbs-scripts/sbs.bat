@echo off

java -DSBS_HOME="%USERPROFILE%\.sbs" -DSBS_ROOT="%SBS_ROOT%" -Xmx1024m -jar "%SBS_ROOT%\target\SBS-dist\SBS.jar" %*