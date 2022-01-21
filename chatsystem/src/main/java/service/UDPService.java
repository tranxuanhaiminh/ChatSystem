package service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import chatsystem.Contact;
import chatsystem.ContactList;
import chatsystem.Conversation;
import chatsystem.MessagesManager;
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
		UDPSend.send("DC", IpAddress.getBroadcast());
		new Connect(Interfacedisplay.modifybutton);
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
		Contact contact = ContactList.findIp(ip);
		ContactList.removeContact(contact);

		///////////////////////////////////////////////////////////////////////
		Conversation conv;
		if ((conv = MessagesManager.getConv(contact)) != null) {
			MessagesManager.removeConv(conv);
		} else if ((conv = MessagesManager.getStoppedConv(contact)) != null) {
			MessagesManager.removeStoppedConv(conv);
		}
		///////////////////////////////////////////////////////////////////////

	}

	/**
	 * Source send new contact either to verify or to add to the contact list
	 * 
	 * @param contact
	 */
	public void udpNew(Contact contact) {
		// Get my contact
		Contact me = ContactList.getMe();
		
		// Get the contact pseudo
		String pseudo = contact.getPseudo();
		
		// If the source pseudo is not duplicated
		if (!ContactList.isDupPseudo(pseudo) && (me == null || !pseudo.equals(me.getPseudo()))) {
			// If the source IP is new
			if (ContactList.findIp(contact.getIpaddress()) == null) {
				ContactList.addContact(contact);
			}
			// If the source IP already existed
			else {
				ContactList.findIp(contact.getIpaddress()).setPseudo(contact.getPseudo());
			}
		}
		// If the source pseudo is duplicated
		else {
			UDPSend.send("DUP", contact.getIpaddress());
		}
	}

	public static void setTimer() {
		startVerification = System.currentTimeMillis();
	}
}
