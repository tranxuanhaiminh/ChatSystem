package networkconnection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IpAddress {

	public static void main(String[] args) throws IOException {
		System.out.println("Hello World");
		
		// Get all network interfaces
		Enumeration e = NetworkInterface.getNetworkInterfaces();
		while(e.hasMoreElements())
		{
			// Get all ip addresses of each interfaces (Normally only 1 each and not treated if many)
		    NetworkInterface n = (NetworkInterface) e.nextElement();
		    Enumeration ee = n.getInetAddresses();
		    
		    // For each ip address on this machine 
		    while (ee.hasMoreElements())
		    {
		    	System.out.println("this is an ip adress");
		        InetAddress i = (InetAddress) ee.nextElement();
		        System.out.println(i.getHostAddress());
		        //System.out.println(i.isAnyLocalAddress());
		        //System.out.println(i.isLinkLocalAddress());
		        //System.out.println(i.isLoopbackAddress());
		        System.out.println(i.isSiteLocalAddress());
		        
		        // Return the address correspond to this machine in INSA network
		        if (i.isSiteLocalAddress()) {
		        	// return i;
		        	System.out.println("The ip address of this machine is : " + i.getHostAddress());
		        }
		        
		    }
		}
	}
}
