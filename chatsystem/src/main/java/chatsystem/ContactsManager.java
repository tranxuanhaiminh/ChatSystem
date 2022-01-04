package chatsystem;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
		
		if (this.cc != null) { // COntact manager de la phase de connection
			
			InetAddress broadcast= null;
			
			try {
				broadcast = InetAddress.getByName("10.1.255.255");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			this.ContactSender = new UDPSender("ASK", broadcast);
			
    		long s = System.currentTimeMillis();
			long e = s + 3000;
			this.ContactReceiver.setRunning(true);
        	while ((System.currentTimeMillis() < e)) {
    				
				String[] response = ContactReceiver.receive();
				if (response!=null) {
					
					String msg = response[0];
					String addr = response[1];
					ContactList cl = this.cc.getContactList();
					
					//traitement de la reception de conctacts
					if (!(msg.equals("ASK"))) { //on ne traite que les contacts reçus
						
						/*for (Contact x : cl.getList()) {
			        	System.out.println(x.getPseudo());
			        	}*/
						System.out.println("\n AJOUT DU CONTACT depuis la phase  de connexion" + addr+" "+ msg+" \n");
			        	
						Contact c = new Contact(msg,addr);
						cl.addContact(c);
						
						/*for (Contact x : cl.getList()) {
			        	System.out.println(x.getPseudo());
			        	}*/
						
					} else {
						System.out.println("\n NON TRAITE \n");
						
					}
	    	        	
	    		}
				
	        	this.ContactReceiver.setRunning(false);
        	}
				
				
			} else if (this.mm != null) { //Contact manager après la phase de connection
				this.ContactReceiver.setRunning(true);
				while (this.ContactReceiver.isRunning()) {
					String[] response = ContactReceiver.receive();
					if (response!=null) {
						String msg = response[0];
						String addr = response[1];
	
						ContactList cl = this.mm.getContactList();
						
						//traitement de la reception de conctacts
						if (!(msg.equals("ASK"))) { //on traite les contacts reçus (soit modif soit nouveau
							
							/*for (Contact x : cl.getList()) {
					        	System.out.println(x.getPseudo());
					        }*/
							
							System.out.println("\n AJOUT DU CONTACT pendant un session" + addr+" "+ msg+" \n");
				        	
							Contact c;
							c = cl.exists(addr);
							
					        if (c!= null) {
					        	c.setPseudo(msg);
					        } else {
					        	c = new Contact(msg,addr);
					        	cl.addContact(c);
					        }
					        
					        /*for (Contact x : cl.getList()) {
				        	System.out.println(x.getPseudo());
				        	}*/
							
						} else if (msg.equals("ASK")){ //on traite les envois de son contact
							try {
								this.ContactSender = new UDPSender(this.mm.getMe().getPseudo(), InetAddress.getByName(addr));
							} catch (UnknownHostException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}
				}
			}
		
	}
	
	public UDPReceiver getContactReceiver() {
		return this.ContactReceiver;
	}

}
