package network;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import chatsystem.Contact;
import service.UDPService;

public class UDPReceive extends Thread {

	/* Fields */
	private static DatagramSocket socket;
	private static DatagramPacket packet;
	private static ByteArrayInputStream baos;
	private static ObjectInputStream oos;
	private static int port;
	private static byte[] buffer;
	private UDPService service;
	private InetAddress ip;
	private String msg = null;
	private Contact contact;
	ExecutorService threadpool;

	/* Constructor */
	public UDPReceive() {
		super();
		buffer = new byte[256];
		service = new UDPService();
		threadpool = Executors.newCachedThreadPool();
		start();
	}

	public void run() {
		try {
			socket = new DatagramSocket(port);

			while (true) {
				// Prepare packet to receive
				packet = new DatagramPacket(buffer, buffer.length);
				
				// Ready to receive UDP packets
				socket.receive(packet);

				// Get the IP address of the source
				ip = packet.getAddress();

				// Try to get data in String format
				try {
					baos = new ByteArrayInputStream(buffer);
					oos = new ObjectInputStream(baos);
					msg = (String) oos.readObject();
				
				// If not we get it in Contact format
				} catch (ClassCastException e) {
					baos = new ByteArrayInputStream(buffer);
					oos = new ObjectInputStream(baos);
					contact = (Contact) oos.readObject();
				}

				// If the data is String
				if (msg != null) {
					String[] data = msg.split(":", 2);

					// Let service deal with data asynchronously for each case
					switch (data[0]) {
					case "ASK":
						threadpool.submit(() -> service.udpAsk(ip));
						break;
					case "DUP":
						threadpool.submit(() -> service.udpDup(ip));
						break;
					case "DC":
						threadpool.submit(() -> service.udpDc(ip));
						break;
					}
					
				// If the data is Contact
				} else {
					threadpool.submit(() -> service.udpNew(contact));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		threadpool.shutdown();

	}
}
