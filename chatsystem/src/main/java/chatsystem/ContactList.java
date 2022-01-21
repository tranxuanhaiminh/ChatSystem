package chatsystem;

import java.net.InetAddress;
import java.util.ArrayList;

import network.IpAddress;
import userinterface.MainMenu;

public class ContactList {
	
	/* Fields */
	private static ArrayList<Contact> list = new ArrayList<Contact>();
	private static Contact me = null;
	
	/* Methods */
	public static void addContact(Contact c) {
		list.add(c);
		try {
			MainMenu.addUser(c.getPseudo(), true);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Got ya");
		}
	}
	
	public static void removeContact(Contact c) {
		list.remove(c);
//		try {
			MainMenu.removeUser(c.getPseudo());
//		} catch (NullPointerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public static boolean isDupPseudo(String pseudo) {
		for (Contact contact : list) {
			if (contact.getPseudo().equalsIgnoreCase(pseudo)) {
				return true;
			}
		}
		return false;
	}
	
	public static Contact findIp(InetAddress ip) {
		for (Contact c : list) {
			if (ip.equals(c.getIpaddress()))
				return c;
		}
		return null;
	}
	
	public static Contact findP(String pseudo) {
		for (Contact c : list) {
			if (pseudo.compareTo(c.getPseudo())==0)
				return c;
		}
		return null;
	}
	
	public static ArrayList<Contact> getList() {
		return list;
	}
	
	public static Contact getMe() {
		return me;
	}
	
	public static void setMe(String pseudo) {
		me = new Contact(pseudo, IpAddress.getIp());
	}
}
