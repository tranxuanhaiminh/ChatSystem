package networkconnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Broadcast {
	
	private static DatagramSocket socket = null;
	private static int userlistPort = 5723;
	
	public static void broadcastPseudo(String pseudo, InetAddress address) throws IOException {
		socket = new DatagramSocket();
		socket.setBroadcast(true);
		
		byte[] buffer = pseudo.getBytes();
		
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, userlistPort);
		socket.send(packet);
		socket.close();
	}

}
