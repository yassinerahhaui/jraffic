#!/bin/zsh

javac -d build Main.java traffic/*.java
java -cp build Main