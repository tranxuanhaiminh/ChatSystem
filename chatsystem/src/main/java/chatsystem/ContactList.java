package chatsystem;

import java.util.ArrayList;

public class ContactList { //
	
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
		int n = list.size();
		for (int i=0; i<n; i++) {
			if (list.get(i)==null)
				System.out.println("null");
			if (c.getPseudo().compareTo(list.get(i).getPseudo())==0) {
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
	
	public ArrayList<Contact> getList() {
		return list;
	}
}
