package test;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import entities.Message;
import network.MsgSender;
import network.UDPSend;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		int port = 58799;
//		try {
//			DatagramSocket socket = new DatagramSocket(port);
//			socket.close();
//		} catch (SocketException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Socket problem occured");
//		}
//		String host = "127.0.0.1";
//		int i = 58799;
//		if (test(host, i)) {
//			System.out.println("Server is listening on port " + i + " of localhost");
//		} else {
//			System.out.println("Server is not listening on port " + i + " of localhost");
//		}
//		UDPSend.send("HELLO", InetAddress.getByName("localhost"));
		// MsgSender tcpsend = new MsgSender(new
		// Socket(InetAddress.getByName("localhost"), 5555));
		// tcpsend.closeSend();
		// tcpsend.send(new Message());
	}

//	public static Boolean test(String host, int port) {
//		try {
//			Socket s = new Socket(host, port);
//			s.close();
//			return true;
//		} catch (IOException e) {
//			return false;
//		}
//	}
}
