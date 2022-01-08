package chatsystem;

import networkconnection.TCPClient1;
import networkconnection.TCPServer1;

public class MessagesManager {
	
	public MainMenu1 mm;
	public TCPClient1 chatClient;
	public TCPServer1 chatServer;
	
	public MessagesManager(MainMenu1 m) {
		this.mm= m;
		//this.chatClient = new TCPClient1();
		
		
	}

}
