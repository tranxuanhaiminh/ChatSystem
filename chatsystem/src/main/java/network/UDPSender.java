package network;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class UDPSender{
	
	private DatagramSocket sendersocket;
	private int port;
	private String message;
	public InetAddress dest;
	
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
		try {
			this.dest = InetAddress.getByName("255.255.255.255");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public UDPSender(String message, InetAddress addr) throws BindException {
		this.port= 58799;
		try {
			this.sendersocket = new DatagramSocket();
		} catch (BindException e) {
			System.out.println("already running \n");
			throw e; 
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
	
	public UDPSender(String message, String addr) throws BindException {
		this.port= 58799;
		try {
			this.sendersocket = new DatagramSocket();
		} catch (BindException e) {
			System.out.println("already running \n");
			throw e; 
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
		try {
			this.dest = InetAddress.getByName(addr);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void send(){
		DatagramPacket out=null;
		System.out.println("On envoie un datagram.\n");
		
		out = new DatagramPacket(message.getBytes(),message.length(), dest, port);
		
		try {
			sendersocket.send(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sendersocket.close();
		System.out.println("Fermeture du socket d'envoi\n");
	}
	
	
	public static void main(String[] args) {
		
			UDPSender u=null;
			try {
				u = new UDPSender("ASK","255.255.255.255");
			} catch (BindException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			u.send();
		
	}
}
