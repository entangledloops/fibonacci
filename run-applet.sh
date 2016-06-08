#!/bin/bash

if [ -z $JAVA_HOME ]; then
	>&2 echo "Java 1.8+ must be installed and JAVA_HOME must be set before using this script."
	exit 1
fi

$JAVA_HOME/bin/javac RowHighlighter.java Fibonacci.java
$JAVA_HOME/bin/appletviewer Fibonacci.java