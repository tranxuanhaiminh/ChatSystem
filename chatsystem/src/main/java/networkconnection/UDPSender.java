package networkconnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class UDPSender{
	
	private DatagramSocket sendersocket;
	private int port;
	private String message;
	public String dest;
	
	public UDPSender(String message) {
		this.port= 58799;
		
		try {
			this.sendersocket = new DatagramSocket();
		} catch (SocketException e) {
			System.out.println("Erreur lors de la creation du socket d'envoi\n");
		}

		try {
			sendersocket.setBroadcast(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.message = message;
		this.dest = "255.255.255.255";
	}
	
	public UDPSender(String message, String addr) {
		this.port= 58799;
		try {
			this.sendersocket = new DatagramSocket();
		} catch (SocketException e) {
			System.out.println("Erreur lors de la creation du socket d'envoi\n");
		}

		try {
			sendersocket.setBroadcast(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.message = message;
		this.dest = addr;
	}
	
	
	public void send(){
		DatagramPacket out=null;
		System.out.println("On envoie un datagram.\n");
		try {
			out = new DatagramPacket(message.getBytes(),message.length(), InetAddress.getByName(dest), port);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			sendersocket.send(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sendersocket.close();
		System.out.println("Fermeture du socket d'envoi\n");
	}
	
	
	public static void main(String[] args) {
		
			UDPSender u = new UDPSender("ASK","255.255.255.255");
			u.send();
		
	}
}
