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

	public boolean comparePseudo(Contact c) {
		boolean isUnique = true;
		int n = list.size();
		for (int i=0; i<n; i++)
			if (c.getPseudo().compareTo(list.get(i).getPseudo())==0)
				isUnique = false;
		return isUnique;
	}
	
	public ArrayList<Contact> getList() {
		return list;
	}
}
