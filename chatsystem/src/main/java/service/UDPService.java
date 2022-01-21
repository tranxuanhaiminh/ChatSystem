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
		if (System.currentTimeMillis() >= (startVerification + 10000)) {
			new Alert("An error occured. Please choose a new username!");
			System.out.println("Error really occured");
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
		// If the source pseudo is not duplicated
		System.out.println("New contact added whose name is " + contact.getPseudo());
		if (!ContactList.isDuplicatedPseudo(contact.getPseudo()) && !contact.equals(ContactList.getMe())) {

			System.out.println("New contact added " + contact.getPseudo());
			// If the source IP is new
			if (ContactList.findIp(contact.getIpaddress()) == null) {
				ContactList.addContact(contact);
				System.out.println("New contact added " + contact.getPseudo());
			}
			// If the source IP already existed
			else {
				ContactList.findIp(contact.getIpaddress()).setPseudo(contact.getPseudo());
			}
		}
		// If the source pseudo is duplicated
		else {

			System.out.println("New contact added whose name is " + contact.getPseudo());
			UDPSend.send("DUP", contact.getIpaddress());
		}
	}

	public void setTimer() {
		startVerification = System.currentTimeMillis();
	}

	public static void main(String[] args) throws UnknownHostException {
		Contact contact = new Contact("abc", InetAddress.getByName("10.1.5.30"));

		System.out.println(ContactList.getMe());
		String test = "abc";

		System.out.println(test.equals(null));
	}
}
