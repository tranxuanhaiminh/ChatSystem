package chatsystem;

import java.util.ArrayList;

public class ContactList {
	
	private ArrayList<Contact> list;
	
	public ContactList() {
		this.list = new ArrayList<Contact>();
	}
	
	
	
	public void addContact(Contact c) {
		list.add(c);
	}
	
	public void removeContact(Contact c) {
		list.remove(c);
	}

	public boolean comparePseudo(Contact c) {
		boolean isUnique = true;
		for (Contact ct : this.list) {
			if (ct==null) // pourquoi ??
				System.out.println("null");
			if (c.getPseudo().compareTo(ct.getPseudo())==0) {
				isUnique = false;
			}
		}
		return isUnique;
	}
	
	public Contact exists(String ip) {
		Contact res= null;
		for (Contact c : this.list) {
			if (ip.compareTo(c.getIpaddress())==0)
				res = c;
		}
		return res;
	}
	
	public Contact existsP(String pseudo) {
		Contact res= null;
		for (Contact c : this.list) {
			if (pseudo.compareTo(c.getPseudo())==0)
				res = c;
		}
		return res;
	}
	
	public ArrayList<Contact> getList() {
		return list;
	}
}
