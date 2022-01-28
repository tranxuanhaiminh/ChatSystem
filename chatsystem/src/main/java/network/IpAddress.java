package network;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IpAddress {

	/* Fields */
	private static InetAddress ipaddress;
	private static InetAddress broadcast;

	/* Methods */

	/**
	 * Set the Ip address and broadcast address of this machine
	 */
	public static void getAddresses() {
		try {
			// Get all network interfaces
			Enumeration<?> e = NetworkInterface.getNetworkInterfaces();
			
			// Get all ip addresses of each interfaces
			while (e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				for (InterfaceAddress interfaceAddress : n.getInterfaceAddresses()) {
					InetAddress i = interfaceAddress.getAddress();
					InetAddress bcast = interfaceAddress.getBroadcast();
					
					// Find the correct address
					if (i.isSiteLocalAddress() && bcast != null) {
						ipaddress = i;
						broadcast = bcast;
						break;
					}
				}
			}
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/* Getters */
	public static InetAddress getIp() {
		return ipaddress;
	}

	public static InetAddress getBroadcast() {
		return broadcast;
	}
}