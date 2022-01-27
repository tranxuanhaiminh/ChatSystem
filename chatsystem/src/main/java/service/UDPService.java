package service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import entities.Contact;
import entities.ContactList;
import entities.Conversation;
import entities.ConversationList;
import network.IpAddress;
import network.UDPSend;
import ressources.Interfacedisplay;
import userinterface.Alert;
import userinterface.ChatWindow;
import userinterface.Connect;
import userinterface.MainMenu;

public class UDPService {
	/* Fields */
	private static Contact me;
	private static long startVerification;

	/**
	 * Send my contact to the source
	 * 
	 * @param ip
	 */
	public void udpAsk(InetAddress ip) {
		me = ContactList.getMe();
		if (me != null) {
			UDPSend.send(me, ip);
		}
	}

	/**
	 * Disconnect from the system and alert the user
	 * 
	 * @param ip
	 */
	public void udpDup(InetAddress ip) {
		// Disconnect the user and ask for new username
		UDPSend.send("DC", IpAddress.getBroadcast());
		new Connect(Interfacedisplay.modifybutton);

		// Display and alert window
		if (startVerification != 0 && System.currentTimeMillis() >= (startVerification + 10000)) {
			new Alert("An error occured. Please choose a new username!");
		} else {
			new Alert("Username existed. Please choose a new username!");
		}
	}

	/**
	 * Remove the disconnected user from contact list and conversations
	 * 
	 * @param ip
	 */
	public void udpDc(InetAddress ip) {

		// Remove the disconnected user from contact list
		Contact contact = ContactList.findContact(ip);
		ContactList.removeContact(contact);
		try {
			MainMenu.modUser(contact.getPseudo(), contact.getPseudo(), false);
		} catch (NullPointerException e) {
		}

		// Remove the disconnected user from the conversation list
		Conversation conv;
		if ((conv = ConversationList.findConv(ip)) != null) {
			ConversationList.removeConv(conv);
		}
		ConversationList.getWindow(ip).setConv(null);
		ConversationList.getWindow(ip).dispose();
	}

	/**
	 * Set halfclose flag of the conversation to true in order to start closing TCP
	 * connection when needed
	 * 
	 * @param ip
	 */
	public void udpHalfClose(InetAddress ip) {
		ConversationList.findConv(ip).setHalfClose(true);
	}

	/**
	 * Set halfclose flag of the conversation to false in order to prevent closing
	 * TCP connection when not needed
	 * 
	 * @param ip
	 */
	public void udpReopen(InetAddress ip) {
		ConversationList.findConv(ip).setHalfClose(false);
	}

	/**
	 * Source send new contact either to verify or to add to the contact list
	 * 
	 * @param contact
	 */
	public void udpNew(Contact contact) {
		// Get my contact
		Contact me = ContactList.getMe();

		// Get the pseudo of new contact
		String pseudo = contact.getPseudo();

		// Get the IP address of new contact
		InetAddress ip = contact.getIpaddress();

		// If the source pseudo is not duplicated
		if (!ContactList.isDupPseudo(pseudo) && (me == null || !pseudo.equals(me.getPseudo()))) {
			// If the source IP already existed
			if (ContactList.findContact(ip) != null) {
				ContactList.findContact(ip).setPseudo(contact.getPseudo());
				try {
					ConversationList.findConv(ip).getChatw().setTitle(contact.getPseudo());
				} catch (NullPointerException e) {
				}
			} else if (ContactList.findOffline(ip) != null) {
				String oldPseudo = ContactList.findOffline(ip).getPseudo();
				ContactList.addContact(contact);
				try {
					MainMenu.modUser(oldPseudo, pseudo, true);
				} catch (NullPointerException e) {
				}
				// If the source IP has an opened chat window
				ChatWindow chatW;
				if ((chatW = ConversationList.getWindow(ip)) != null) {
					// establish a tcp connection and show chat window
					chatW.setConv(null);
					chatW.dispose();
					Conversation conversation;
					try {
						conversation = new Conversation(new Socket(contact.getIpaddress(), 55555));
						conversation.startChat();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			// If the source IP is new
			else {
				ContactList.addContact(contact);
				try {
					MainMenu.addUser(pseudo, true);
				} catch (NullPointerException e) {
				}
			}
		}
		// If the source pseudo is duplicated
		else {
			UDPSend.send("DUP", ip);
		}
	}

	/**
	 * Start timer for verification
	 */
	public static void setTimer() {
		startVerification = System.currentTimeMillis();
	}
}
