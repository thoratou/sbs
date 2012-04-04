#!/bin/sh

rm -rf ${2}/include
mkdir -p ${2}
cp -rf ${1}/include/ ${2}

