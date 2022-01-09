package chatsystem;

import networkconnection.MsgSender;
import networkconnection.MsgReceiver;

public class MessagesManager { // chaque conversation est géré par un Msgsender et un Msgreceiver
	
	public MainMenu1 mm;
	public MsgSender chatClient;
	public MsgReceiver chatServer;
	
	public MessagesManager(MainMenu1 m) {
		this.mm= m;
		//this.chatClient = new TCPClient1();
		
		
	}

}
