package test;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.sun.security.ntlm.Client;

import chatsystem.Contact;
import network.UDPSend;
import service.UDPService;
import userinterface.Alert;

public class Test {
	
	
	private static boolean test = true;
	
	public static boolean getTest() {
		return test;
	}
	
	public static void setTest(boolean newtest) {
		test = newtest;
	}

	public static void main(String[] args) throws UnknownHostException {
		
		InetAddress ip = InetAddress.getByName("127.0.0.1");
		// InetAddress ip = InetAddress.getByName("10.1.5.30");
		Contact contact = new Contact("abcdefghi", ip);
		
		UDPSend.send(contact, InetAddress.getByName("localhost"));
		
		System.out.println(InetAddress.getByName("localhost").getCanonicalHostName());
		
//		new Alert("Your username has already been chosen. Please choose a new username!");
//		new Modify();
		
		System.out.println(test);
		test = false;
		System.out.println(test);
		
		
		Contact c1 = new Contact("abc", InetAddress.getByName("localhost"));
		System.out.println(c1.ContactToString());
		InetAddress client = InetAddress.getByName("localhost");
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
//			oos.writeObject(c1);
			oos.writeObject("Hello this is an UDP packet");
//			oos.flush();
			// get the byte array of the object
			byte[] Buf = baos.toByteArray();

//			int number = Buf.length;
//			;
//			byte[] data = new byte[4];
//
//			// int -> byte[]
//			for (int i = 0; i < 4; ++i) {
//				int shift = i << 3; // i * 8
//				data[3 - i] = (byte) ((number & (0xff << shift)) >>> shift);
//			}
//
			DatagramSocket socket = new DatagramSocket(1233);
//			DatagramPacket packet = new DatagramPacket(data, 4, client, 1234);
//			socket.send(packet);

			// now send the payload
			DatagramPacket packet = new DatagramPacket(Buf, Buf.length, client, 1234);
			socket.send(packet);

			System.out.println("DONE SENDING");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Testing " + client.getCanonicalHostName());
		
		test("I'm a String");
		test(c1);
	}
	
	private static void test(Object obj) {
		if (obj instanceof String) {
			System.out.println(obj + " is String\n");
		} else if (obj instanceof Contact) {
			System.out.println(((Contact)obj).ContactToString() + " is Contact\n");
		} else {
			System.out.println("WTF?\n");
		}
	}

}
