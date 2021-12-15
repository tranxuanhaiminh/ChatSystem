package networkconnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;


import chatsystem.Connection;
import chatsystem.Contact;
import chatsystem.ContactList;
import chatsystem.MainMenu1;


public class BroadcastReceiver extends Thread {
	
	private static DatagramSocket socket;
	private boolean running;
	private int port = 57254;
    private byte[] buf = new byte[256];
    private MainMenu1 mm=null;
    private Connection mc=null;
    
	
	public BroadcastReceiver(MainMenu1 me) {
		super();
		this.mm = me;
	}
	
	public BroadcastReceiver(Connection me) {
		super();
		System.out.println("Ajout de la page de connexion dans b receiver\n");
		this.mc = me;
	}
	
	public void run() {
		
		try {
			socket = new DatagramSocket(port);
			socket.setBroadcast(true);
			if (this.mc!=null)
				socket.setSoTimeout(3000);
			
			while(true) {
				
				buf = new byte[256];
				
				System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!\n");
				
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				System.out.println(getClass().getName() + ">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
		        System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));
		        
		        String msg = new String(packet.getData()).trim();
		        
		        //recuperer la bonne addresse
		        ArrayList<InetAddress> m= new ArrayList<InetAddress>();
		        Enumeration e = NetworkInterface.getNetworkInterfaces();
				while(e.hasMoreElements())
				{
					// Get all ip addresses of each interfaces (Normally only 1 each and not treated if many)
				    NetworkInterface n = (NetworkInterface) e.nextElement();
				    Enumeration ee = n.getInetAddresses();
				    
				    // For each ip address on this machine 
				    while (ee.hasMoreElements())
				    {
				        InetAddress i = (InetAddress) ee.nextElement();
				        m.add(i);
				    }
				}
				
				InetAddress dest = packet.getAddress();
				System.out.println("*******************************"+dest+"******************************");
				        
				if (!(m.contains(dest))) {
					if (!(msg .equals("RequestPseudos"))) {
				        if (mm != null) { //modification d'un contact ou ajout d'une nouvelle connexion
				        	
					        Contact c;
					        ContactList cl = mm.getContactList();
					        c = cl.exists(packet.getAddress().getHostAddress());
					        
					        for (Contact x : cl.getList()) {
					        	System.out.println(x.getPseudo());
					        }
					        
					        System.out.println("\n AJOUT DU CONTACT " + packet.getAddress().getHostAddress()+" "+ msg+" \n");
					        
					        if (c != null)
					        	c.setPseudo(msg);
					        else {
					        	cl.addContact(new Contact(msg, packet.getAddress().getHostAddress()));
					        }
					        
					        for (Contact x : cl.getList()) {
					        	System.out.println(x.getPseudo());
					        }
					        
				        } else { // phase de connexion : recuperation des conatcts
				        	
				        	System.out.println("\n AJOUT DU CONTACT depuis la phase  de connexion" + packet.getAddress().getHostAddress()+" "+ msg+" \n");
				        	
					        ContactList cl = mc.getContactList();
				        	
					        for (Contact x : cl.getList()) {
					        	System.out.println(x.getPseudo());
					        }
							
					        cl.addContact(new Contact(msg, packet.getAddress().getHostAddress()));
					        cl.addContact(new Contact("jojo", "sfjbfkjsdbf"));
					        
					        for (Contact x : cl.getList()) {
					        	System.out.println(x.getPseudo());
					        }
				        
				        }
			        
					} else {
						if (mm != null) { // c'est lorsqu'on utilise l'app qu'on envoie ses infos
							
			        		byte[] sendData = mm.getMe().getPseudo().getBytes();
			        		System.out.println("\nEnvoi de nous même\n");
			        		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
			        		socket.send(sendPacket);
						}
			        } 
					
				} else {
					System.out.println(getClass().getName() + " paquet non traité" + "\n\n");
				}
			}
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			socket.close();
			System.out.println("Le socket de connection est supprime, tous les contacts devraient être reçus\n");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setMe(String pseudo) {
		//mm.getMe().setPseudo(pseudo);
	}

}
