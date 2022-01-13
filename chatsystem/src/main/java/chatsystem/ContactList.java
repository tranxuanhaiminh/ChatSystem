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
		boolean isUnique = true;
		for (Contact ct : list) {
			if (ct==null) // si jamais des contacts nulls sont dans la list
				System.out.println("Contact null");
			if (c.getPseudo().equals(ct.getPseudo())) {
				isUnique = false;
			}
		}
		return isUnique;
	}
	
	public Contact findIp(InetAddress ip) {
		Contact res= null;
		for (Contact c : list) {
			if (ip.equals(c.getIpaddress()))
				res = c;
		}
		return res;
	}
	
	public Contact findP(String pseudo) {
		Contact res= null;
		for (Contact c : list) {
			if (pseudo.compareTo(c.getPseudo())==0)
				res = c;
		}
		return res;
	}
	
	public ArrayList<Contact> getList() {
		return list;
	}
}
