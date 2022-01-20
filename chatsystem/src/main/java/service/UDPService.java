package service;

import java.net.InetAddress;

import chatsystem.Contact;
import chatsystem.ContactList;
import chatsystem.Conversation;
import chatsystem.MessagesManager;
import network.IpAddress;
import network.UDPSend;
import userinterface.Alert;
import userinterface.Modify;

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
			startVerification = System.currentTimeMillis();
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
		new Modify().setVisible(true);
		if (System.currentTimeMillis() >= (startVerification + 10000)) {
			new Alert("An error occured. Please choose a new username!").setVisible(true);
		} else {
			new Alert("Username existed.").setVisible(true);
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
	 * @param contact
	 */
	public void udpNew(Contact contact) {

		// If the source pseudo is not duplicated
		if (!ContactList.comparePseudo(contact)) {
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

}
