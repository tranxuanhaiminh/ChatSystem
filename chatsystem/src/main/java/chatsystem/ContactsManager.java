package chatsystem;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

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
		this.ContactReceiver = null;
		try {
			this.ContactReceiver = new UDPReceiver();
		} catch (BindException e) {
			e.printStackTrace();
			this.cc.getAlreadyRunning().display();
			this.cc.getAlreadyRunning().requestFocus();
		} catch (SocketException e) {
			e.printStackTrace();
			this.cc.getProblem().display();
			this.cc.getAlreadyRunning().requestFocus();

		}
	}
	
	public void run() {
		
		while (true) {
			//on dirait qu'il faut nécessairement au moins une instruction
			try {
				Thread.sleep(1);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
				this.cc.getProblem().display();
			}			
			int i=0;
			
			while (this.isRunning()) {
				i++;
				//System.out.println("is running \n");
				
				///////////////////////////////////////////////////// Contact manager de la phase de connection
				
				if (state == false && i==1) {
					
					
					try {
						(new UDPSender("ASK")).send();
						System.out.println("Envoi de la demande de contacts pendant la phase de connection.\n");
					} catch (BindException e2) {
						e2.printStackTrace();
						this.cc.getAlreadyRunning().display();
						
					} catch (SocketException e1) {
						e1.printStackTrace();
						this.cc.getProblem().display();
						
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
						this.cc.getProblem().display();
						
					} catch (IOException e1) {
						e1.printStackTrace();
						this.cc.getProblem().display();
						
					}
					 
					System.out.println("Lancement r�ception des contacts pendant la connection\n");
					
					long s = System.currentTimeMillis();
					long e = s + 3000;
					
			    	while ((System.currentTimeMillis() <= e) && (this.ContactReceiver.isRunning())) {
			    		
			    		DatagramSocket rs = ContactReceiver.getReceiversocket();
			    		
			    		try {
							rs.setSoTimeout((int)(e-System.currentTimeMillis()));
						} catch (SocketException e1) {
							e1.printStackTrace();
							this.cc.getProblem().display();
						}
			    		
						String[] response=null;
						try {
							response = ContactReceiver.receive(); // cette fonction est bloquante
						} catch (IOException e2) {
							e2.printStackTrace();
							this.cc.getProblem().display();
						} 
						
						if (response!=null) {
							
							String msg = response[0];
							String addr = response[1];
							ContactList cl = this.cc.getContactList();
							
							//traitement de la reception de conctacts
							if ((msg.equals("ASK"))) {
								
								//on ne fait rien 
								
							} else if (msg.equals("DISCONNECTED")) {
								
								Contact c=null;
								try {
									c = this.cc.getContactList().findIp(InetAddress.getByName(addr));
								} catch (UnknownHostException e1) {
									e1.printStackTrace();
									this.cc.getProblem().display();
								}
								
								if (c!=null) {
									c.delPseudo();
									this.cc.getContactList().removeContact(c);
									System.out.println("Un contact s'est d�connect� pendant la phase de connexion !\n");
								}
								
							}else {
								
								//pour eviter le double ajout lorsuq'il y a modification d'un contact pendant la connexion
								Contact cin=null;
								try {
									cin = this.cc.getContactList().findIp(InetAddress.getByName(addr));
								} catch (UnknownHostException e1) {
									e1.printStackTrace();
									this.cc.getProblem().display();
								}
								
								if (cin==null) {
									System.out.println("\nAJOUT DU CONTACT depuis la phase  de connexion" + addr+" "+ msg+" \n");
									cl.addContact(new Contact(msg,addr));
								} else {
									System.out.println(cin.getIpaddress()+" a modifi� son pseudo pendant la phase  de connexion ! New username= "+ msg+" \n");
									cin.setPseudo(msg);
								}
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
						e1.printStackTrace();
						this.cc.getProblem().display();

					}
	
				} else if (state == true) {
			    	
					////////////////////////////////////Contact manager apr�s la phase de connection
	
					MainMenu main = this.cc.getMain();
					while (main == null) {
						try {
							Thread.sleep(10); // on attends que les autres donn�es soient misent � jour
						} catch (InterruptedException err) {
							err.printStackTrace();
							this.cc.getProblem().display();
						}
						main = this.cc.getMain();
					}
					//on envoie son contact aux autres 
			    	
					try {
						(new UDPSender(main.getMe().getPseudo())).send();
						System.out.println("\nENVOI de son contact � tout le monde !\n");

					}  catch (BindException e1) {
						e1.printStackTrace();
						this.cc.getAlreadyRunning();
					} catch (SocketException e) {
						e.printStackTrace();
						main.getProblem().display();
					} catch (UnknownHostException e) {
						e.printStackTrace();
						main.getProblem().display();
					} catch (IOException e) {
						e.printStackTrace();
						main.getProblem().display();
					}
					
					//lancement du receiver
					System.out.println("Lancement receiver lors de la session\n");
					this.ContactReceiver.setRunning(true);
	
					while (this.isRunning()) {
						
						/////////////////////////////gestion reception de contact /////////////////////////////
						String[] response=null;
						try {
							response = ContactReceiver.receive();
						} catch (IOException e1) {
							e1.printStackTrace();
							main.getProblem().display();
						}
						
						if (response!=null) {
							String msg = response[0];
							String addr = response[1];
			
							ContactList cl = main.getContactList();
							
							//traitement de la reception de conctacts
							if (msg.equals("ASK")){ //on traite les envois de son contact
								
								try {
									(new UDPSender(main.getMe().getPseudo(), addr)).send();
							    	System.out.println("\nENVOI de son contact � "+ addr+"\n");

								} catch (BindException e) {
									e.printStackTrace();
									this.cc.getAlreadyRunning().display();
									
								} catch (IOException e) {
									e.printStackTrace();
									main.getProblem().display();
									
								}
								
	
							} else if (msg.equals("DISCONNECTED")) {
								Contact c=null;
								try {
									c = cl.findIp(InetAddress.getByName(addr));
									
								} catch (UnknownHostException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									main.getProblem().display();

								}
								
						        if (c== null) {
						        	System.out.println("Ce contact n'etait pas dans la liste de vos contacts.\n");
						        	
						        } else {
						        	System.out.println("\n SUPPRESSION DU CONTACT pendant la session " + msg+" "+ addr +" \n");
						        	main.getContactList().removeContact(c);
						        	
						        	//modif connected users
						        	String oldusername = c.getPseudo();
						        	c.delPseudo();
						        	System.out.println("Modification de la liste des contacts affich�s\n" + main.modUser(c.getPseudo(), main.getDisconnected(), oldusername)+ "\n");
						        	
						        	// mettre � jour la conversation
						        	main.getMessMan().removeConv(main.getMessMan().getConv(c));
						        }
						        
							} else { //on traite les contacts re�us (soit modif soit nouveau)
								
								Contact c=null;
								try {
									c = cl.findIp(InetAddress.getByName(addr));
									
								} catch (UnknownHostException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									main.getProblem().display();

								}
								
						        if (c!= null) {
						        	System.out.println("\n MODIF D'UN CONTACT pendant un session " + msg+" "+ addr +" \n");
						        	
						        	//modifier la liste des contacts
						        	String oldusername = c.getPseudo();
						        	c.setPseudo(msg);
						        	System.out.println("Modification de la liste des contacts"+main.modUser(c.getPseudo(), main.getConnected(), oldusername)+"\n");
						        	
						        } else {
						        	c = new Contact(msg,addr);
						        	System.out.println("\n AJOUT DU CONTACT pendant une session " + msg+" "+ addr +" \n");
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
			try {
				(new UDPSender(m, ad)).send();
			} catch (BindException e) {
				e.printStackTrace();
				this.cc.getAlreadyRunning();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.cc.getProblem().setVisible(true);
			}
	}
	
}
