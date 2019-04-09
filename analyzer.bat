@echo off
javac -cp ".;lib/*" analyzer/*.java
java -cp ".;transcriptAnalyzer.jar;lib/*" analyzer.Analyzer