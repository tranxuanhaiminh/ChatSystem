package networkconnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import chatsystem.Connection;
import chatsystem.MainMenu1;

public class BroadcastSender {
	
	private static DatagramSocket socket;
	private static int port = 5723;
	
	public void broadcastToAllUsers(String msg, InetAddress address) throws IOException {
		socket = new DatagramSocket();
		socket.setBroadcast(true);
		System.out.println("Socket on port " + socket.getPort() + " of type broadcast : "+ socket.getBroadcast() + " ready!");
		
		byte[] buffer = msg.getBytes();
		
		
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
		socket.send(packet);
		System.out.println("Sent packet with data : " + msg + " to address " + address.toString() + " of name " + address.getCanonicalHostName() + " on port " + String.valueOf(port));
		socket.close();
		System.out.println("socket closed");
	}
}