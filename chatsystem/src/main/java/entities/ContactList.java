package entities;

import java.net.InetAddress;
import java.util.ArrayList;

import userinterface.MainMenu;

public class ContactList {
	
	/* Fields */
	private static ArrayList<Contact> list = new ArrayList<Contact>();
	private static Contact me = null;
	
	/* Methods */
	
	/**
	 * Add new contact to contact list and main menu table if exists
	 * @param Contact
	 */
	public static void addContact(Contact c) {
		list.add(c);
		try {
			MainMenu.addUser(c.getPseudo(), true);
		} catch (NullPointerException e) {}
	}
	
	/**
	 * Remove new contact from contact list and main menu table if exists
	 * @param Contact
	 */
	public static void removeContact(Contact c) {
		list.remove(c);
		try {
			MainMenu.removeUser(c.getPseudo());
		} catch (NullPointerException e) {}
	}
	
	/**
	 * True if pseudo existed in the list | False if not existed
	 * @param String
	 * @return boolean
	 */
	public static boolean isDupPseudo(String pseudo) {
		for (Contact contact : list) {
			if (contact.getPseudo().equalsIgnoreCase(pseudo)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Find contact from IP address
	 * @param InetAddress
	 * @return Contact
	 */
	public static Contact findContact(InetAddress ip) {
		for (Contact c : list) {
			if (ip.equals(c.getIpaddress()))
				return c;
		}
		return null;
	}

	/**
	 * Find contact from pseudo
	 * @param pseudo
	 * @return Contact
	 */
	public static Contact findContact(String pseudo) {
		for (Contact c : list) {
			if (pseudo.compareTo(c.getPseudo())==0)
				return c;
		}
		return null;
	}
	
	/**
	 * Create my contact
	 * @param pseudo
	 */
	public static void setMe(String pseudo) {
		me = new Contact(pseudo);
	}

	
	/* Getters */
	public static ArrayList<Contact> getList() {
		return list;
	}
	
	public static Contact getMe() {
		return me;
	}
	
}
