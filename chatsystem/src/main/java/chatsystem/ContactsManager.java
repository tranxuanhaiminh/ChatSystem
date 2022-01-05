package chatsystem;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ContactsManager extends Thread{
	
	private Connection cc;
	private UDPReceiver ContactReceiver;
	private UDPSender ContactSender;
	private boolean sendMe= false;
	private boolean run_co=false;
	private boolean state; // false = connecting phase true = Main 
	private boolean running;
	
	
	public ContactsManager(Connection c) {
		this.cc = c;
		this.running = false;
		this.state = false;
		this.ContactReceiver = new UDPReceiver();
	}
	
	public void run() {
		
		while (true) {
			//System.out.println("."); //on dirait qu'il faut nécessairement au moins une instruction
			try {
				Thread.sleep(1);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}			
			int i=0;
			while (this.isRunning()) {
				i++;
				//System.out.println("is running \n");
			// Contact manager de la phase de connection
			InetAddress broadcast= null;
			
			try {
				broadcast = InetAddress.getByName("255.255.255.255");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// Contact manager de la phase de connection
			if (state == false && i==1) {
				System.out.println("Envoi de la demande de contact (connection)\n");
				this.ContactSender = new UDPSender("ASK", broadcast);
				
				System.out.println("Lancement réception des contacts pendant la connection\n");
				
				long s = System.currentTimeMillis();
				long e = s + 3000;
				
		    	while ((System.currentTimeMillis() < e) && (this.ContactReceiver.isRunning())) {
		    		
		    		DatagramSocket rs = ContactReceiver.getReceiversocket();
		    		
		    		try {
						rs.setSoTimeout(3000);
					} catch (SocketException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String[] response = ContactReceiver.receive(); // cette fonction est bloquante
					
					if (response!=null) {
						
						String msg = response[0];
						String addr = response[1];
						ContactList cl = this.cc.getContactList();
						cl = new ContactList();
						Contact t = new Contact("toto2","130.1.1.0");
						cl.addContact(t);
						
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
		        	
		    	}
		    	
				this.ContactReceiver.setRunning(false);
		    	System.out.println("Fin de la reception des contacts phase de connection\n");
		    	
		    	DatagramSocket rs = ContactReceiver.getReceiversocket();
	    		
	    		try {
					rs.setSoTimeout(0);
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	
			} else if (state == true) {
				
		    	try {
					Thread.sleep(1000); // on attends que les autres données soient misent à jour
				} catch (InterruptedException err) {
					// TODO Auto-generated catch block
					err.printStackTrace();
				}
		    	
				//Contact manager après la phase de connection
		    	
		    	System.out.println("\nEnvoi de son contact aux autres\n");
				//on envoie son contact aux autres 
				this.setSendMe();
				
				//lancement du receiver
				System.out.println("Lancement receiver lors de la session\n");
				this.ContactReceiver.setRunning(true);
				//this.ContactReceiver.isRunning() && this.cc.getMain()!=null
				
				while (this.isRunning()) {
					//gestion de l'envoi de son contact
					if (sendMe = true) {
				    	System.out.println("\nEnvoi de son contact aux autres après modif\n");
						this.ContactSender = new UDPSender(this.cc.getMain().getMe().getPseudo(), broadcast);
						sendMe = false;
					}
						
					String[] response = ContactReceiver.receive();
					if (response!=null) {
						String msg = response[0];
						String addr = response[1];
		
						ContactList cl = this.cc.getMain().getContactList();
						
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
								
								this.ContactSender = new UDPSender(this.cc.getMain().getMe().getPseudo(), InetAddress.getByName(addr));
						    	System.out.println("\nEnvoi de son contact à "+ addr+"\n");

							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			}
			}
		}
	}
	
	public UDPReceiver getContactReceiver() {
		return this.ContactReceiver;
	}
	public UDPSender getContactSender() {
		return this.ContactSender;
	}
	
	public void setSendMe() {
		sendMe = true;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void setState() {
		state = true;
	}

}
