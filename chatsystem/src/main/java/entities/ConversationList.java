package entities;

import java.net.InetAddress;
import java.util.ArrayList;

import userinterface.ChatWindow;

public class ConversationList {

	/* Fields */
	private static ArrayList<Conversation> convList = new ArrayList<Conversation>();
	private static ArrayList<ChatWindow> windowList = new ArrayList<ChatWindow>();

	/* Methods */
	
	/**
	 * Add new conversation to the list
	 * @param conv
	 */
	public static void addConv(Conversation conv) {
		convList.add(conv);
	}
	
	/**
	 * Remove the conversation from the list and close TCP connection
	 * @param conv
	 */
	public static void removeConv(Conversation conv) {
		conv.close();
		convList.remove(conv);
		for (ChatWindow window : windowList) {
			if (conv.getDest().getIpaddress().equals(window.getContact().getIpaddress())) {
				window.setConv(null);
				break;
			}
		}
	}

	/**
	 * Find conversation from the given username
	 * @param pseudo
	 * @return Conversation or null if not found
	 */
	public static Conversation findConv(String pseudo) {
    	if (convList == null) return null;
		for (Conversation conv : convList) {
			if (conv.getDest().getPseudo().equals(pseudo)) {
				return conv;
			}
		}
		return null;
	}

	/**
	 * Find conversation from the given IP address
	 * @param pseudo
	 * @return Conversation or null if not found
	 */
	public static Conversation findConv(InetAddress ip) {
    	if (convList == null) return null;
		for (Conversation conv : convList) {
			if (conv.getDest().getIpaddress().equals(ip)) {
				return conv;
			}
		}
		return null;
	}
	
	public static void addWindow(ChatWindow chatW) {
		windowList.add(chatW);
	}
	
	/**
	 * Get the window correspond to the contact
	 * @param contact
	 * @return chatwindow or null if not in window list
	 */
	public static ChatWindow getWindow(InetAddress ip) {
		for (ChatWindow window : windowList) {
			if (ip.equals(window.getContact().getIpaddress())) {
				return window;
			}
		}
		return null;
	}
}