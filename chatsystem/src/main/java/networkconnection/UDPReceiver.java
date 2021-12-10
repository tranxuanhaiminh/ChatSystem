package networkconnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPReceiver extends Thread {

	private DatagramSocket socket;
	private boolean running;
	private int port = 4445;
	private byte[] buf = new byte[256];

	public UDPReceiver() {
		super();
		start();
	}

	public void run() {

		running = true;

		try {
			socket = new DatagramSocket(port, InetAddress.getByName("localhost"));

			while (running) {
				System.out.println(getClass().getName() + ">>>Ready to receive UDP packets!");
				
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				System.out.println(getClass().getName() + ">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
		        System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));
		        
		        String msg = new String(packet.getData(), 0, packet.getLength());

		        packet = new DatagramPacket(buf, buf.length, packet.getAddress(), packet.getPort());

				if (msg.equals("end")) {
					running = false;
					continue;
				}

				socket.send(packet);
				
			}
			socket.close();

		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}