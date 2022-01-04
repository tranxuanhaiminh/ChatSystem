package networkconnection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IpAddress {

	public static InetAddress getAddress() throws IOException {
		
		InetAddress iaddress = null;
		
		ArrayList<InterfaceAddress> addresslist = new ArrayList<InterfaceAddress>();

		// Get all network interfaces
		Enumeration e = NetworkInterface.getNetworkInterfaces();
		while (e.hasMoreElements()) {
			// Get all ip addresses of each interfaces (Normally only 1 each and not treated if many)
			NetworkInterface n = (NetworkInterface) e.nextElement();
			
			for (InterfaceAddress interfaceAddress : n.getInterfaceAddresses()) {
				InetAddress i = interfaceAddress.getAddress();
//				System.out.println("this is an ip adress");
//				System.out.println(i.getHostAddress());
//				System.out.println(i.isAnyLocalAddress());
//				System.out.println(i.isLinkLocalAddress());
//				System.out.println(i.isLoopbackAddress());
//				System.out.println(i.isSiteLocalAddress());
//				InetAddress broadcast = interfaceAddress.getBroadcast();
//				if (broadcast != null) {
//					System.out.println(interfaceAddress.toString());
//					System.out.println("address : " + i.toString() + " and broadcast : " + broadcast.toString());					
//				}
				if (i.isSiteLocalAddress()) {
					iaddress = i;
				}
			}
		}
		return iaddress;
	}

	public static void main(String[] args) {
		IpAddress ipAddress = new IpAddress();
		try {
			ArrayList<InterfaceAddress> addresslist = ipAddress.getAddress();
			for (InterfaceAddress i : addresslist) {
				System.out.println(i.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
