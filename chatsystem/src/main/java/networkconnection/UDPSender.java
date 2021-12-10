package networkconnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPSender {
	private DatagramSocket socket;
	private InetAddress address;

	private byte[] buf;

	public UDPSender() {
		try {
			socket = new DatagramSocket();
			address = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public String sendEcho(String msg) {
		buf = msg.getBytes();
		DatagramPacket packet 
		= new DatagramPacket(buf, buf.length, address, 4445);


		try {
			socket.send(packet);
			packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String received = new String(
				packet.getData(), 0, packet.getLength());
		return received;
	}

	public void close() {
		socket.close();
	}
}
