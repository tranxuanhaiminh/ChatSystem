package entities;

import java.net.Socket;

import network.MsgReceiver;
import network.MsgSender;
import network.UDPSend;
import userinterface.ChatWindow;

public class Conversation {

	/* Fields */
	private MsgSender sender;
	private Contact dest;
	private ChatWindow chatW;
	private boolean halfClosed;

	/* Constructor */
	public Conversation(Socket socket) {
		dest = ContactList.findContact(socket.getInetAddress());
		sender = new MsgSender(socket);
		halfClosed = false;
		ConversationList.addConv(this);
		System.out.println("test");
		new MsgReceiver(socket, this);
	}

	/* Methods */
	
	/**
	 * Show chat window and signal the other end
	 */
	public void startChat() {
		UDPSend.send("RO", dest.getIpaddress());
		chatW = new ChatWindow(this);
	}

	/**
	 * Start closing the TCP connection
	 */
	public void close() {
		sender.closeSend();
	}

	/**
	 * Send message
	 * @param message
	 */
	public void sendChat(Message message) {
		sender.send(message);
	}
	
	/* Getters and setters */
	public Contact getDest() {
		return dest;
	}

	public ChatWindow getChatw() {
		return chatW;
	}

	public boolean getHalfClose() {
		return halfClosed;
	}

	public void setHalfClose(boolean value) {
		halfClosed = value;
	}
}