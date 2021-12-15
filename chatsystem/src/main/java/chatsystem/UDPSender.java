package chatsystem;

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
	public InetAddress dest;
	
	
	public UDPSender(String message, InetAddress addr) {
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
		
		DatagramPacket out = new DatagramPacket(message.getBytes(),message.length(), dest, port);
		
		try {
			sendersocket.send(out);
		} catch (IOException e) {
			System.out.println("Erreur lors de l'envoi du msg \n");
		}
		
		sendersocket.close();
		System.out.println("Fermeture du socket d'envoi\n");
	}
	
	
	public static void main(String[] args) {
		
		//UDPReceiver r = new UDPReceiver();
		//r.start();
		
		try {
			UDPSender u = new UDPSender("Test", InetAddress.getByName("10.1.255.255"));
			u.send();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
