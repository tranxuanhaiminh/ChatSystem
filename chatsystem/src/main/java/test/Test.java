package test;

import entities.Contact;
import entities.ContactList;
import entities.ConversationList;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ContactList.addContact(new Contact("test1"));
		for (Contact contact : ContactList.getList()) {
			System.out.println(contact.getPseudo());
		}
		ContactList.findContact("test1").setPseudo("test2");
		for (Contact contact : ContactList.getList()) {
			System.out.println(contact.getPseudo());
		}
		System.out.println(ConversationList.findConv("test3").getChatw().getChatInput().getText());
	}

}
