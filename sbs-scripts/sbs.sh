#!/bin/sh

java -DSBS_HOME="$HOME/.sbs" -DSBS_ROOT="%SBS_ROOT%" -Xmx1024m -jar "$SBS_ROOT/target/SBS-dist/SBS.jar" $@
