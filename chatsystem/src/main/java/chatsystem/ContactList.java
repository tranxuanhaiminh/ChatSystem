package chatsystem;

import java.net.InetAddress;
import java.util.ArrayList;

public class ContactList {
	
	private static ArrayList<Contact> list = new ArrayList<Contact>();
	
	public void addContact(Contact c) {
		list.add(c);
	}
	
	public void removeContact(Contact c) {
		list.remove(c);
	}

	public boolean comparePseudo(Contact c) {
		for (Contact ct : list) {
			if (ct==null)
				System.out.println("Contact null ??? This is not normal.\n");
			else if (c.getPseudo().equals(ct.getPseudo())) {
				return false;
			}
		}
		return true;
	}
	
	public Contact findIp(InetAddress ip) {
		for (Contact c : list) {
			if (ip.equals(c.getIpaddress()))
				return c;
		}
		return null;
	}
	
	public Contact findP(String pseudo) {
		for (Contact c : list) {
			if (pseudo.compareTo(c.getPseudo())==0)
				return c;
		}
		return null;
	}
	
	public ArrayList<Contact> getList() {
		return list;
	}
}
