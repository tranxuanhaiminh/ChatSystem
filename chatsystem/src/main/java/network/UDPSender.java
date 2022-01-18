package network;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import userinterface.Alert;

public class UDPSender {

	/*
	 * Fields
	 */
	private DatagramSocket sendersocket;
	private int port;
	private String message;
	public InetAddress dest;

	/*
	 * Constructor
	 */
	public UDPSender(String message) {
		this.port = 58799;
		this.message = message;
		try {
			this.sendersocket = new DatagramSocket();
			sendersocket.setBroadcast(true);
			this.dest = InetAddress.getByName("255.255.255.255");
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
            new Alert("Error : Please close the program (connecting phase) ! ").setVisible(true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            new Alert("Error : Please close the program (connecting phase) ! ").setVisible(true);
		}
	}

	public UDPSender(String message, InetAddress addr) {
		this.port = 58799;
		this.message = message;
		this.dest = addr;
		try {
			this.sendersocket = new DatagramSocket();
			sendersocket.setBroadcast(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            new Alert("Error : Please close the program (connecting phase) ! ").setVisible(true);
		}
	}

	public UDPSender(String message, String addr) {
		this.port = 58799;
		this.message = message;

		try {
			this.sendersocket = new DatagramSocket();
			sendersocket.setBroadcast(true);
			this.dest = InetAddress.getByName(addr);

		} catch (SocketException e) {
			System.out.println("Erreur lors de la creation du socket d'envoi\n");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Methods
	 */
	public void send() {
		DatagramPacket out = null;
		System.out.println("On envoie un datagram.\n");
		out = new DatagramPacket(message.getBytes(), message.length(), dest, port);
		try {
			sendersocket.send(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            new Alert("Error : Please close the program (connecting phase) ! ").setVisible(true);
		}
		sendersocket.close();
		System.out.println("Fermeture du socket d'envoi\n");
	}
}
