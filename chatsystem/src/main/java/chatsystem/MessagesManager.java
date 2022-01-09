package chatsystem;

import networkconnection.TCPClient;
import networkconnection.TCPServer;

public class MessagesManager {
	
	public MainMenu1 mm;
	public TCPClient chatClient;
	public TCPServer chatServer;
	
	public MessagesManager(MainMenu1 m) {
		this.mm= m;
		//this.chatClient = new TCPClient1();
		
		
	}

}
