package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.AbstractMap.SimpleEntry;

import entities.Contact;
import entities.ContactList;
import entities.ConversationList;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Entry<String, String>> pairList = new ArrayList<>();
		Entry<String, String> pair1 = new SimpleEntry<>("KEY1", "VAL1");
		Entry<String, String> pair2 = new SimpleEntry<>("KEY2", "VAL2");
		pairList.add(pair1);
		System.out.println(pairList.contains(new SimpleEntry<>("KEY1", "")));
		pairList.add(pair2);
		System.out.println(pairList.contains(pair2));
		
		try {
			System.out.println(InetAddress.getByName("10.1.5.31"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
