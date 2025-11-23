.PHONY: build run

build:
	javac *.java -d build

run: build
	java -cp build Jraffic