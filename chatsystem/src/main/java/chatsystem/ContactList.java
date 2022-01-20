package chatsystem;

import java.net.InetAddress;
import java.util.ArrayList;

import network.IpAddress;

public class ContactList {
	
	/* Fields */
	private static ArrayList<Contact> list = new ArrayList<Contact>();
	private static Contact me = null;
	
	/* Methods */
	public static void addContact(Contact c) {
		list.add(c);
	}
	
	public static void removeContact(Contact c) {
		list.remove(c);
	}

	public static boolean comparePseudo(Contact c) {
		for (Contact ct : list) {
			if (ct==null)
				System.out.println("Contact null ??? This is not normal.\n");
			else if (c.getPseudo().equals(ct.getPseudo())) {
				return false;
			}
		}
		return true;
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
