@echo off

echo "Java 1.8+ must be installed and JAVA_HOME must be on your path to use this script."

javac RowHighlighter.java Fibonacci.java
appletviewer Fibonacci.java