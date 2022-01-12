build: clean
	@echo "\n==== COMPILING ====\n"
	mvn compile
    
connect : build
	@echo "\n==== LOADING CONNECTION ====\n"
	mvn package
	java -cp target/chatsystem-0.0.1-SNAPSHOT.jar userinterface.Connect

clean:
	@echo "\n==== CLEANING ====\n"
	-rm target/chatsystem-0.0.1-SNAPSHOT.jar
	
test:
	@echo "\n==== LOADING test ====\n"
	mvn package
	java -cp target/chatsystem-0.0.1-SNAPSHOT.jar network.UDPReceiver

