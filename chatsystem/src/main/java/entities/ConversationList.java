package entities;

import java.net.InetAddress;
import java.util.ArrayList;

public class ConversationList {

	/* Fields */
	private static ArrayList<Conversation> convList;

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
	}

	/**
	 * Find conversation from the given username
	 * @param pseudo
	 * @return Conversation or null if not found
	 */
	public static Conversation findConv(String pseudo) {
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
		for (Conversation conv : convList) {
			if (conv.getDest().getIpaddress().equals(ip)) {
				return conv;
			}
		}
		return null;
	}
}