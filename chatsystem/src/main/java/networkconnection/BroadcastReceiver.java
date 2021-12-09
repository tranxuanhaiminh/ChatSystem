package networkconnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class BroadcastReceiver extends Thread {
	
	private static DatagramSocket socket;
	private boolean running;
	private int port = 5723;
    private byte[] buf = new byte[256];
	
	public BroadcastReceiver() {
		super();
		start();
	}
	public void run() {
		
		String pseudo = "toTO";
		
		try {
			socket = new DatagramSocket(port);
			socket.setBroadcast(true);
			
			while(true) {
				System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");
				
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				System.out.println(getClass().getName() + ">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
		        System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));
		        
		        String msg = new String(packet.getData()).trim();
		        
		        if(msg == "RequestPseudos") {
		        	byte[] sendData = pseudo.getBytes();
		        	DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
		        	socket.send(sendPacket);
		        }
			}
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
