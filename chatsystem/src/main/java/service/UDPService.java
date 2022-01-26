package service;

import java.net.InetAddress;

import entities.Contact;
import entities.ContactList;
import entities.Conversation;
import entities.ConversationList;
import network.IpAddress;
import network.UDPSend;
import ressources.Interfacedisplay;
import userinterface.Alert;
import userinterface.Connect;

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
		ContactList.removeContact(ContactList.findContact(ip));

		// Remove the disconnected user from the conversation list
		Conversation conv;
		if ((conv = ConversationList.findConv(ip)) != null) {
			ConversationList.removeConv(conv);
		}

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
	 * Set halfclose flag of the conversation to false in order to prevent closing TCP
	 * connection when not needed
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

		// If the source pseudo is not duplicated
		if (!ContactList.isDupPseudo(pseudo) && (me == null || !pseudo.equals(me.getPseudo()))) {
			// If the source IP is new
			if (ContactList.findContact(contact.getIpaddress()) == null) {
				ContactList.addContact(contact);
			}
			// If the source IP already existed
			else {
				ContactList.findContact(contact.getIpaddress()).setPseudo(contact.getPseudo());
			}
		}
		// If the source pseudo is duplicated
		else {
			UDPSend.send("DUP", contact.getIpaddress());
		}
	}

	/**
	 * Start timer for verification
	 */
	public static void setTimer() {
		startVerification = System.currentTimeMillis();
	}
}
