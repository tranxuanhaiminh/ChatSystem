package chatsystem;

import java.net.DatagramSocket;
import java.net.SocketException;

import network.UDPReceiver;
import network.UDPSender;
import userinterface.Connect;
import userinterface.MainMenu;

public class ContactsManager extends Thread{
	
	private Connect cc;
	private UDPReceiver ContactReceiver;
	private boolean state; // false = connecting phase true = Main 
	private boolean running;
	
	
	public ContactsManager(Connect c) {
		this.cc = c;
		this.running = false;
		this.state = false;
		this.ContactReceiver = new UDPReceiver();
	}
	
	public void run() {
		
		while (true) {
			//on dirait qu'il faut nécessairement au moins une instruction
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
				
				///////////////////////////////////////////////////// Contact manager de la phase de connection
				
				if (state == false && i==1) {
					
					System.out.println("Envoi de la demande de contact (connection)\n");
					
					//this.ContactSender = new UDPSender("ASK");
					//this.ContactSender.send();
					(new UDPSender("ASK")).send();
					 
					System.out.println("Lancement réception des contacts pendant la connection\n");
					
					long s = System.currentTimeMillis();
					long e = s + 3000;
					
			    	while ((System.currentTimeMillis() <= e) && (this.ContactReceiver.isRunning())) {
			    		
			    		DatagramSocket rs = ContactReceiver.getReceiversocket();
			    		
			    		try {
							rs.setSoTimeout((int)(e-System.currentTimeMillis()));
						} catch (SocketException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			    		
						String[] response = ContactReceiver.receive(); // cette fonction est bloquante
						
						if (response!=null) {
							
							String msg = response[0];
							String addr = response[1];
							ContactList cl = this.cc.getContactList();
							
							//traitement de la reception de conctacts
							if (!(msg.equals("ASK"))) { //on ne traite que les contacts reçus
								
								System.out.println("\nAJOUT DU CONTACT depuis la phase  de connexion" + addr+" "+ msg+" \n");
					        	
								Contact c = new Contact(msg,addr);
								cl.addContact(c);
								
							} else {
								System.out.println("\n NON TRAITE \n");
							}
			    	        	
			    		} else {
			    			System.out.println("On a recu null.\n");
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
			    	
					////////////////////////////////////Contact manager après la phase de connection
	
					MainMenu main = this.cc.getMain();
					
					//on envoie son contact aux autres 
			    	
					System.out.println("\nENVOI de son contact aux autres\n");
					/*this.ContactSender = new UDPSender(this.cc.getMain().getMe().getPseudo());
					this.ContactSender.send();*/
					(new UDPSender(this.cc.getMain().getMe().getPseudo())).send();
					
					//lancement du receiver
					System.out.println("Lancement receiver lors de la session\n");
					this.ContactReceiver.setRunning(true);
	
					while (this.isRunning()) {
						
						/////////////////////////////gestion reception de contact /////////////////////////////
						String[] response = ContactReceiver.receive();
						if (response!=null) {
							String msg = response[0];
							String addr = response[1];
			
							ContactList cl = main.getContactList();
							
							//traitement de la reception de conctacts
							if (msg.equals("ASK")){ //on traite les envois de son contact
								
								//this.ContactSender = new UDPSender(this.cc.getMain().getMe().getPseudo(), addr);
								//this.ContactSender.send();
								(new UDPSender(main.getMe().getPseudo(), addr)).send();
						    	System.out.println("\nENVOI de son contact � "+ addr+"\n");
	
							} else if (msg.equals("DISCONNECTED")) {
								Contact c;
								c = cl.exists(addr);
								
						        if (c== null) {
						        	System.out.println("Ce contact n'est pas dans la liste de vos contacts\n");
						        } else {
						        	System.out.println("\n SUPPRESSION DU CONTACT pendant la session " + msg+" "+ addr +" \n");
						        	main.getContactList().removeContact(c);
						        	
						        	//modif connected users
						        	System.out.println("Modification de la liste des contacts" + main.modUser(c.getIpaddress(), main.getDisconnected(), c.getPseudo())+ "\n");
						        	c.setPseudo(null);
						        	
						        	// mettre à jour la conversation
						        	main.getMessMan().removeConv(c);
						        }
						        
							} else { //on traite les contacts reçus (soit modif soit nouveau
								
								Contact c;
								c = cl.exists(addr);
								
						        if (c!= null) {
						        	System.out.println("\n MODIF D'UN CONTACT pendant un session " + msg+" "+ addr +" \n");
						        	
						        	//modifier la liste des contacts
						        	System.out.println("Modification de la liste des contacts"+main.modUser(msg, main.getConnected(), c.getPseudo())+"\n");
						        	
						        	//modifier le contact
						        	c.setPseudo(msg);
						        	
						        } else {
						        	c = new Contact(msg,addr);
						        	System.out.println("\n AJOUT DU CONTACT pendant un session " + msg+" "+ addr +" \n");
						        	cl.addContact(c);
						        	main.addUser(c.getPseudo(), main.getConnected());
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

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void setState() {
		state = true;
	}
	
	//////////////////gestion de l'envoi de msg udp
	public void signalDatagram(String m, String ad) {
			System.out.println("Sending : "+ m +" to " + ad+" .\n");
			(new UDPSender(m, ad)).send();
	}
	
}
