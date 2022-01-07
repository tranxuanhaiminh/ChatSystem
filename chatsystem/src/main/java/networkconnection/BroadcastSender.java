package networkconnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class BroadcastSender {
	
	private static DatagramSocket socket;
	private static int port = 5723;
	private String respondmsg = null;
    private byte[] buf = new byte[256];
    private long start;
    private long finish;
	
	public String broadcastToAllUsers(String msg, InetAddress address, boolean ret) throws IOException {
		
		// Create socket and set it to broadcast mode
		socket = new DatagramSocket();
		socket.setBroadcast(true);
		
		System.out.println("Socket on port " + socket.getPort() + " of type broadcast : "+ socket.getBroadcast() + " ready!");
		
		// Create and broadcast packet
		byte[] buffer = msg.getBytes();
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
		socket.send(packet);
		
		System.out.println("Sent packet with data : " + msg + " to address " + address.toString() + " of name " + address.getCanonicalHostName() + " on port " + String.valueOf(port));
		
		// Start timer for broadcast respond
		start = System.currentTimeMillis();
		while (ret) {
			finish = System.currentTimeMillis();
			if (finish - start > 3000) {
				ret = false;
				continue;
			}
			DatagramPacket inpacket = new DatagramPacket(buf, buf.length);
			socket.receive(inpacket);
			respondmsg = new String(packet.getData()).trim();
			ret = false;
		}
		
		socket.close();
		System.out.println("socket closed");
		
		return respondmsg;
	}
}