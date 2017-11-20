.PHONY: compile
compile:
	javac -d ./build `find ./src -name '*.java'`

.PHONY: run
run:
	java -cp ./build Main

.PHONY: clean
clean:
	rm -f ./build/*.class
