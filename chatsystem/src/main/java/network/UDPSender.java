package network;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

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
	public UDPSender(String message) throws SocketException, UnknownHostException, BindException {
		this.port = 58799;
		this.message = message;
		this.sendersocket = new DatagramSocket();
		sendersocket.setBroadcast(true);
		this.dest = InetAddress.getByName("255.255.255.255");
	}

	public UDPSender(String message, InetAddress addr) throws SocketException, BindException {
		this.port = 58799;
		this.message = message;
		this.dest = addr;
		this.sendersocket = new DatagramSocket();
		sendersocket.setBroadcast(true);
	}

	public UDPSender(String message, String addr) throws BindException {
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
	public void send() throws IOException {
		DatagramPacket out = null;
		System.out.println("On envoie un datagram.\n");
		out = new DatagramPacket(message.getBytes(), message.length(), dest, port);
		sendersocket.send(out);
		sendersocket.close();
		System.out.println("Fermeture du socket d'envoi\n");
	}
}
