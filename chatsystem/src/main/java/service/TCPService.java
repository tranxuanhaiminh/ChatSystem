package service;

import java.net.InetAddress;
import java.net.Socket;

import database.Databasecon;
import entities.ContactList;
import entities.Conversation;
import entities.ConversationList;
import entities.Message;
import userinterface.MainMenu;

public class TCPService {

	/**
	 * Notify user of new message and add message to database
	 * @param message
	 * @param conv
	 */
	public static void msgReceive(Message message, Conversation conv) {
		if (conv.getChatw().isVisible()) {
			// Displaying the message
			conv.getChatw().addChatLine(message, false);
		} else {
			// Notify user on the main menu
			MainMenu.notifyMessage(conv.getDest().getPseudo());
		}
		// adding to the chat history
		Databasecon.insertChat(conv.getDest().getIpaddress().getHostAddress(),
				conv.getDest().getPseudo(), message.toString(), message.getDate(), false);
	}
	
	/**
	 * Establish a new TCP connection to receive message
	 * @param socket
	 */
	public static void newTCP(Socket socket) {
		
		// Get the IP address
		InetAddress ip = socket.getInetAddress();
		
		// If this is one of our contact
		if (ContactList.findContact(ip) != null) {
			
			// If we don't have a tcp connection with this machine yet
			if (ConversationList.findConv(ip) == null) {
				new Conversation(socket);
			}
		}
	}
}
