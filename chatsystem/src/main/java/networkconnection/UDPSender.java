package networkconnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSender {
	
	private DatagramSocket socket;
	private int port;
	
	public void sendUDP(String msg, InetAddress address) throws IOException {
		socket = new DatagramSocket();

		System.out.println("Socket on port " + socket.getPort() + " of type broadcast : "+ socket.getBroadcast() + " ready!");
		
		byte[] buffer = msg.getBytes();
		
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
		socket.send(packet);
		System.out.println("Sent packet with data : " + msg + " to address " + address.toString() + " of name " + address.getCanonicalHostName() + " on port " + String.valueOf(port));
		socket.close();
		System.out.println("socket closed");
	}
}
