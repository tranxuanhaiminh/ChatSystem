package networkconnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import chatsystem.Connection;
import chatsystem.Contact;
import chatsystem.ContactList;
import chatsystem.MainMenu1;


public class BroadcastReceiver extends Thread {
	
	private static DatagramSocket socket;
	private boolean running;
	private int port = 5723;
    private byte[] buf = new byte[256];
    private MainMenu1 mm=null;
    private Connection mc=null;
    
	
	public BroadcastReceiver(MainMenu1 me) {
		super();
		start();
		this.mm = me;
	}
	
	public BroadcastReceiver(Connection me) {
		super();
		start();
		this.mc = me;
	}
	
	public void run() {
		
		try {
			socket = new DatagramSocket(port);
			socket.setBroadcast(true);
			
			while(true) {
				
				buf = new byte[256];
				
				System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");
				
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				System.out.println(getClass().getName() + ">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
		        System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));
		        
		        String msg = new String(packet.getData()).trim();
		        
		        //recuperer la bonne add
		        InetAddress m;
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
				        if (i.isSiteLocalAddress()) 
				        	m=i;

				if (packet.getAddress() != m) {
					
			        if (mm != null) {
			        	
				        Contact c;
				        ContactList cl = mm.getContactList();
				        c = cl.exists(packet.getAddress().getHostAddress());
				        
				        if (c != null)
				        	cl.removeContact(c);
				        
				        cl.addContact(new Contact(msg, packet.getAddress().getHostAddress()));
			        
				        
			        } else   {
			        
				        ContactList cl = mc.getContactList();
				        cl.addContact(new Contact(msg, packet.getAddress().getHostAddress()));
			        
			        }
			        
			        if(msg == "RequestPseudos") {
		        		byte[] sendData = mm.getMe().getPseudo().getBytes();
		        		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
		        		socket.send(sendPacket);
			        } 
				} else {
					System.out.println(getClass().getName() + "paquet non traité" + "\n\n");
				}
			}
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setMe(String pseudo) {
		//mm.getMe().setPseudo(pseudo);
	}
	
}
