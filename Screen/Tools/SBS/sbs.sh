#!/bin/sh

java -DSBS_ROOT="$SBS_ROOT" -Xmx1024m -cp "$SBS_ROOT/target/SBS.jar" screen.tools.sbs.Main $@
