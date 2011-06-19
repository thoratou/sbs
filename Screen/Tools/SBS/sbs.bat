@echo off

java -DSBS_ROOT="%SBS_ROOT%" -Xmx1024m -cp "%SBS_ROOT%\target\SBS-dist\SBS.jar;%SBS_ROOT%\target\SBS-dist\lib\*" screen.tools.sbs.Main %*