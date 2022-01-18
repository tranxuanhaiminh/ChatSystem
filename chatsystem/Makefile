build:
	@echo "\n==== COMPILING ====\n"
	mvn compile
	@echo "\n==== Fat Jar CREATION ====\n"
	mvn package
    
start :
	@echo "\n==== LOADING CONNECTION ====\n"
	java -cp target/ChatSystem-jar-with-dependencies.jar chatsystem.ChatSystem

clean:
	@echo "\n==== CLEANING ====\n"
	-rm *.db
	mvn clean
