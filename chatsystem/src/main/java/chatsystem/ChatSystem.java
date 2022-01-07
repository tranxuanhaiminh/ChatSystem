package chatsystem;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.util.ArrayList;

import networkconnection.IpAddress;

public class ChatSystem {

	private static InetAddress ipadd = null;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ipadd = IpAddress.getAddress();
		
		System.out.println(ipadd.toString());
	}

}
