all: run

clean:
	rm -f src/main/java/*.class out/Main.jar

Main.jar: out/parcs.jar src/main/java/*.java
	@javac -cp out/parcs.jar src/main/java/*.java
	@jar cf out/Main.jar -C src/main/java .
	@rm -f src/main/java/*.class

run: Main.jar
	@cd out && java -cp 'parcs.jar:Main.jar' Main
