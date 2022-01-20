package test;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import chatsystem.Contact;

public class Test2 {
	

	public static void main(String[] args) {
		boolean test2;
		test2 = Test.getTest();
		while (Test.getTest()) {			
			System.out.println(test2);
			Test.setTest(false);
		}
		
		ByteArrayInputStream baos;
		ObjectInputStream oos;
		try {
			DatagramSocket socket = new DatagramSocket(1234);
			DatagramPacket packet;
//			byte[] data = new byte[4];
//			packet = new DatagramPacket(data, data.length);
			System.out.println("Accepting UDP packet");
			while (true) {
//				socket.receive(packet);
//				int len = 0;
//				// byte[] -> int
//				for (int i = 0; i < 4; ++i) {
				// len |= (data[3 - i] & 0xff) << (i << 3);
				// }
				//
				// // now we know the length of the payload
				byte[] buffer = new byte[256];
				packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				System.out.println(packet.getPort());
				System.out.println("Packet received");

				String c1;
				Contact c2;
				try {
					baos = new ByteArrayInputStream(buffer);
					oos = new ObjectInputStream(baos);
					c1 = (String) oos.readObject();
					System.out.println(c1);
				} catch (ClassCastException e) {
					// TODO Auto-generated catch block
					baos = new ByteArrayInputStream(buffer);
					oos = new ObjectInputStream(baos);
					c2 = (Contact) oos.readObject();
					System.out.println(c2.ContactToString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
