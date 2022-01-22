package test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import entities.Message;
import network.MsgSender;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			MsgSender tcpsend = new MsgSender(new Socket(InetAddress.getByName("localhost"), 5555));
			tcpsend.closeSend();
			tcpsend.send(new Message());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
