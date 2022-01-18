package network;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

import network.UDPReceiver;

public class UDPReceiver {

	/*
	 * Fields
	 */
	private boolean running;
	private DatagramSocket receiversocket;
	private int port;
	private byte[] buffer;
	private DatagramPacket in;

	/*
	 * Constructor
	 */
	public UDPReceiver() throws SocketException, BindException {

		this.port = 58799;
		this.running = true;
		this.receiversocket = new DatagramSocket(port);
		receiversocket.setBroadcast(true);
	}

	/*
	 * Methods
	 */
	public String[] receive() throws IOException {

		String[] ret = null;
		buffer = new byte[256];
		in = new DatagramPacket(buffer, buffer.length);

		System.out.println("Pret � recevoir \n");

		try {

			receiversocket.receive(in);
			String msg = new String(in.getData(), 0, in.getLength());
			String addr = in.getAddress().getHostName();

			// On r�cup�re toutes nos addresses pour filtrer les messages
			ArrayList<InetAddress> m = new ArrayList<InetAddress>();
			Enumeration<NetworkInterface> e = null;
			e = NetworkInterface.getNetworkInterfaces();

			while (e.hasMoreElements()) {
				// Get all ip addresses of each interfaces (Normally only 1 each and not treated
				// if many)
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration<InetAddress> ee = n.getInetAddresses();

				// For each ip address on this machine
				while (ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();
					m.add(i);
				}
			}
			boolean cond = false;
			cond = !(m.contains(InetAddress.getByName(addr)));
			if (cond) { // on ne peut pas recevoir un msg u'on a envoyé nous même
				ret = new String[] { msg, addr };
			}

		} catch (SocketException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SocketTimeoutException e) {
			System.out.println("Timer expir� fin du receive (connection).\n");
		}

		return ret;

	}

	public void setRunning(boolean b) {
		this.running = b;
	}

	public boolean isRunning() {
		return this.running;
	}

	public DatagramSocket getReceiversocket() {
		return receiversocket;
	}
	
}