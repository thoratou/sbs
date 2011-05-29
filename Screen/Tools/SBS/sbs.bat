@echo off

java -DSBS_ROOT="%SBS_ROOT%" -Xmx1024m -cp "%SBS_ROOT%\target\SBS.jar;%SBS_ROOT%\lib\jdom-1.1.jar;%SBS_ROOT%\lib\jaxen-1.1.jar" screen.tools.sbs.Main %*