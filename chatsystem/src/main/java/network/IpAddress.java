package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class IpAddress {

	/*
	 * Fields
	 */
	private static InetAddress ipaddress;
	private static InetAddress broadcast;
	
	/*
	 * Methods
	 */
	
	/**
	 * Set the Ip address and broadcast address of this machine
	 */
	public static void getAddresses() {
		try {
			// Get all network interfaces
			Enumeration e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {
				// Get all ip addresses of each interfaces (Normally only 1 each and not treated
				// if many)
				NetworkInterface n = (NetworkInterface) e.nextElement();

				for (InterfaceAddress interfaceAddress : n.getInterfaceAddresses()) {
					InetAddress i = interfaceAddress.getAddress();
					InetAddress bcast = interfaceAddress.getBroadcast();
					if (i.isSiteLocalAddress() && bcast != null) {
						ipaddress = i;
						broadcast = bcast;
					}
				}
			}
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/*
	 * Getters
	 */
	
	/**
	 * get Ip address of this machine that is used for the chatsystem
	 * @return InetAddress
	 */
	public static InetAddress getIp() {
		return ipaddress;
	}

	/**
	 * get broadcast address of this machine that is used for the chatsystem
	 * @return InetAddress
	 */
	public static InetAddress getBroadcast() {
		return broadcast;
	}
}