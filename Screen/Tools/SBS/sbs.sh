#!/bin/sh

java -DSBS_ROOT="$HOME/.sbs" -DSBS_HOME="%SBS_HOME%" -Xmx1024m -jar "$SBS_HOME/target/SBS-dist/SBS.jar" $@
