package network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSend {

	/* Fields */
	private static DatagramSocket socket;
	private static DatagramPacket packet;
	private static ByteArrayOutputStream baos;
	private static ObjectOutputStream oos;
	private static int port = 58799;
	private static byte[] buffer;

	/* Methods */
	
	/**
	 * Send the obj to the IP destination
	 * @param obj
	 * @param ip
	 */
	public static void send(Object obj, InetAddress ip) {
		try {
			// Prepare the socket and packet data in the stream
			socket = new DatagramSocket();
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
//			oos.flush();
			buffer = baos.toByteArray();
			packet = new DatagramPacket(buffer, buffer.length, ip, port);

			// Send then close the socket
			socket.send(packet);
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
