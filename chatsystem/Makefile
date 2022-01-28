install:
	@echo "\n==== COMPILING ====\n"
	mvn compile
	@echo "\n==== Fat Jar CREATION ====\n"
	mvn package
    
launch :
	@echo "\n==== LOADING CONNECTION ====\n"
	java -jar target/ChatSystem-jar-with-dependencies.jar
	
clean :
	@echo "\n==== ClEANING ====\n"
	mvn clean
	rm *.db

	
