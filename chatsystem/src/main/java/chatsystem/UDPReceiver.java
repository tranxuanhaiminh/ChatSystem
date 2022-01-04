package chatsystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

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
			System.out.println("Erreur lors de la creation du socket de reception \n");
		}

		try {
			receiversocket.setBroadcast(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public void run(){
		
		while (running) {

			
			
			if (msg.equals("ASK"){
				System.out.println("RECEIVED : "+msg +" FROM "+ in.getAddress().getHostName()+"\n");
				UDPSender s = new UPDSender()
			}
			//System.out.println("RECEIVED : "+msg +" FROM "+ in.getAddress().getHostName()+"\n");
		}
		
		receiversocket.close();
		
	}*/
	
	public String[] receive() {
		
		System.out.println("Pret à recevoir \n");
		buffer = new byte[256];
		in = new DatagramPacket(buffer, buffer.length);
		
		try {
			receiversocket.receive(in);
		} catch (IOException e) {
			System.out.println("Erreur lors de la reception du mess\n");
		}
		
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
		if (cond) // on ne peut pas recevoir un msg u'on a envoyé nous même
			return new String[]{msg,addr};
		else 
			return null;
	}
	
	
	public void setRunning (boolean b) {
		this.running= b;
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
}
