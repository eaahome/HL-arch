#!/bin/bash

REPORT_DIR=report1

rm *.log

/home/eaa/bin/apache-jmeter-5.6.3/bin/jmeter \
 -n \
 -t plan.jmx \
 -l test.log \
 -e \
 -o $REPORT_DIR

# Generate only
#/home/eaa/bin/apache-jmeter-5.6.3/bin/jmeter \
# -g HL\ Architect\ Basic\ Test\ Plan\ Result.csv \
# -o $REPORT_DIR
