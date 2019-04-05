#!/bin/bash
javac -cp ".:lib/*" analyzer/*.java
jar cfm transcriptAnalyzer.jar manifest.txt analyzer/*.class