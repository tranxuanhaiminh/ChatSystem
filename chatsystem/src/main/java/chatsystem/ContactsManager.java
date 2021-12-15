package chatsystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.Timer;

import chatsystem.Message.Type;
import networkconnection.BroadcastSender;

public class ContactsManager extends Thread{
	
	private MainMenu1 mm;
	private Connection cc;
	private UDPReceiver ContactReceiver;
	private UDPSender ContactSender;
	
	
	public ContactsManager(MainMenu1 mm) {
		this.mm = mm;
		this.cc = null;
		this.ContactReceiver = new UDPReceiver();
	}
	
	public ContactsManager(Connection cc) {
		this.mm = null;
		this.cc = cc;
		this.ContactReceiver = new UDPReceiver();
		
	}
	
	public void run() {
		
		
		
		if (this.cc != null) {
			
			InetAddress broadcast= null;
			
			try {
				broadcast = InetAddress.getByName("10.1.255.255");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			this.ContactSender = new UDPSender("ASK", broadcast);
			
			this.ContactReceiver.setRunning(true);
			
			
			Timer t = new Timer(3000, new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	while (ContactReceiver.isRunning()) {
	            		
	    				String msg = ContactReceiver.receive();
	    				
	    				//traitement de la reception de conctacts
	    				if (!(msg.equals("ASK"))) {
	    					
	    					
	    					
	    					
	    				} else {
	    					
	    					
	    					
	    				}
	    				
	    				
	    			}
	            }
			});
	        
	        t.setRepeats(false); // Only execute once
	        t.start();
	        
	        this.ContactReceiver.setRunning(false);
	        
			
			
			
		}
		
	}
	
	public UDPReceiver getContactReceiver() {
		return this.ContactReceiver;
	}

}
