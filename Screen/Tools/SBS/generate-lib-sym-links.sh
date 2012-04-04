#!/bin/sh

mkdir -p ${4}
cd ${4}
rm -f lib${1}.so.${2}
rm -f lib${1}.so
ln -s ${3}/lib${1}.so lib${1}.so.${2}
ln -s lib${1}.so.${2} lib${1}.so
ln -s lib${1}.so ${1}.so

