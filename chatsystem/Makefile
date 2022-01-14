build:
	@echo "\n==== COMPILING ====\n"
	mvn compile
    
start : build
	@echo "\n==== LOADING CONNECTION ====\n"
	mvn package
	java -cp target/ChatSystem.jar

clean:
	@echo "\n==== CLEANING ====\n"
	-rm target/*.jar
	
test:
	@echo "\n==== LOADING test ====\n"
	mvn package
	java -cp target/ChatSystem.jar network.UDPReceiver

