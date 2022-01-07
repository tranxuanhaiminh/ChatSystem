package networkconnection;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

import networkconnection.UDPReceiver;

public class UDPReceiver{
	
	private boolean running;
	private DatagramSocket receiversocket;
	private int port;
	private byte[] buffer;
	private DatagramPacket in;
	
	public UDPReceiver(){
		
		this.port = 58799;
		this.running=true;
		try {
			this.receiversocket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("Erreur lors de la creation du socket de reception \n");
		}

		try {
			receiversocket.setBroadcast(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public String[] receive() {
		
		String[] ret = null;
		
		System.out.println("Pret à recevoir \n");
		
		buffer = new byte[256];
		in = new DatagramPacket(buffer, buffer.length);
		
		try {
			
			receiversocket.receive(in);
			String msg = new String(in.getData(),0,in.getLength());
			String addr = in.getAddress().getHostName();
		
			//On récupère toutes nos addresses pour filtrer les messages
			ArrayList<InetAddress> m= new ArrayList<InetAddress>();
	        Enumeration<NetworkInterface> e = null;
			try {
				e = NetworkInterface.getNetworkInterfaces();
			} catch (SocketException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			while(e.hasMoreElements())
			{
				// Get all ip addresses of each interfaces (Normally only 1 each and not treated if many)
			    NetworkInterface n = (NetworkInterface) e.nextElement();
			    Enumeration<InetAddress> ee = n.getInetAddresses();
			    
			    // For each ip address on this machine 
			    while (ee.hasMoreElements())
			    {
			        InetAddress i = (InetAddress) ee.nextElement();
			        m.add(i);
			    }
			}
			
			
			
			boolean cond = false;
			try {
				cond = !(m.contains(InetAddress.getByName(addr)));
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (cond) { // on ne peut pas recevoir un msg u'on a envoyé nous même
				ret = new String[] {msg,addr};
			}
			
		} catch (SocketTimeoutException e) {
			System.out.println("Timer expiré fin du receive (connection).\n");
		} catch (IOException e) {
			System.out.println("Erreur lors de la reception du mess\n");
			e.printStackTrace();
		}
		
		
		return ret;
		
	}
	
	
	public void setRunning (boolean b) {
		this.running= b;
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	public DatagramSocket getReceiversocket() {
		return receiversocket;
	}
	

	public static void main(String[] args) {
		
		UDPReceiver r = new UDPReceiver();
		while (true) {
			r.receive();
		}

	}
	
}
