package chatsystem;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import network.UDPReceiver;
import network.UDPSender;
import userinterface.Alert;
import userinterface.Connect;
import userinterface.MainMenu;

public class ContactsManager extends Thread {

	/*
	 * Fields
	 */
	private Connect cc;
	private UDPReceiver ContactReceiver;
	private boolean state; // false = connecting phase true = Main
	private boolean running;

	/*
	 * Constructor
	 */
	public ContactsManager(Connect c) {
		this.cc = c;
		this.running = false;
		this.state = false;
		this.ContactReceiver = null;
		this.ContactReceiver = new UDPReceiver();
	}

	/*
	 * Thread run method
	 */
	public void run() {

		while (true) {
			// on dirait qu'il faut nécessairement au moins une instruction
			try {
				Thread.sleep(1);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
				new Alert("Error : Please close the program (connecting phase) ! ").setVisible(true);
			}
			int i = 0;

			while (this.isRunning()) {
				i++;

				///////////////////////////////////////////////////// Contact manager de la
				///////////////////////////////////////////////////// phase de connection

				if (state == false && i == 1) {

					new UDPSender("ASK").send();
					System.out.println("Envoi de la demande de contacts pendant la phase de connection.\n");

					System.out.println("Lancement r�ception des contacts pendant la connection\n");

					long s = System.currentTimeMillis();
					long e = s + 3000;

					while ((System.currentTimeMillis() <= e) && (this.ContactReceiver.isRunning())) {

						DatagramSocket rs = ContactReceiver.getReceiversocket();

						try {
							rs.setSoTimeout((int) (e - System.currentTimeMillis()));
						} catch (SocketException e1) {
							e1.printStackTrace();
							new Alert("Error : Please close the program (connecting phase) ! ").setVisible(true);
						}

						String[] response = null;
						response = ContactReceiver.receive(); // cette fonction est bloquante

						if (response != null) {

							String msg = response[0];
							String addr = response[1];
							ContactList cl = this.cc.getContactList();

							// traitement de la reception de conctacts
							if ((msg.equals("ASK"))) {

								// on ne fait rien

							} else if (msg.equals("DISCONNECTED")) {

								Contact c = null;
								try {
									c = this.cc.getContactList().findIp(InetAddress.getByName(addr));
								} catch (UnknownHostException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								if (c != null) {
									c.delPseudo();
									this.cc.getContactList().removeContact(c);
									System.out.println(
											"Un contact s'est d�connect� pendant la phase de connexion !\n");
								}

							} else {

								// pour eviter le double ajout lorsuq'il y a modification d'un contact pendant
								// la connexion
								Contact cin = null;
								try {
									cin = this.cc.getContactList().findIp(InetAddress.getByName(addr));
								} catch (UnknownHostException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								if (cin == null) {
									System.out.println("\nAJOUT DU CONTACT depuis la phase  de connexion" + addr + " "
											+ msg + " \n");
									cl.addContact(new Contact(msg, addr));
								} else {
									System.out.println(cin.getIpaddress()
											+ " a modifi� son pseudo pendant la phase  de connexion ! New username= "
											+ msg + " \n");
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
						new Alert("Error : Please close the program (connecting phase) ! ").setVisible(true);
					}
		    		
		    		synchronized (this.cc.getContactList()) {
		    			this.cc.getContactList().notify();
		    		}
		    		
				} else if (state == true) {

					//////////////////////////////////// Contact manager apr�s la phase de
					//////////////////////////////////// connection

					(new UDPSender(this.cc.getMe().getPseudo())).send();
					System.out.println("\nENVOI de son contact � tout le monde !\n");

					MainMenu main = this.cc.getMain();
					while (main == null) {
						try {
							Thread.sleep(1); // on attends que les autres donn�es soient misent � jour
						} catch (InterruptedException err) {
							err.printStackTrace();
							new Alert("Error : Please close the program (connecting phase) ! ").setVisible(true);
						}
						main = this.cc.getMain();
					}
					// on envoie son contact aux autres

					// lancement du receiver
					System.out.println("Lancement receiver lors de la session\n");
					this.ContactReceiver.setRunning(true);

					while (this.isRunning()) {

						///////////////////////////// gestion reception de contact
						///////////////////////////// /////////////////////////////
						String[] response = null;
						response = ContactReceiver.receive();

						if (response != null) {
							String msg = response[0];
							String addr = response[1];

							ContactList cl = main.getContactList();

							// traitement de la reception de conctacts
							if (msg.equals("ASK")) { // on traite les envois de son contact

								new UDPSender(main.getMe().getPseudo(), addr).send();
								System.out.println("\nENVOI de son contact � " + addr + "\n");

							} else if (msg.equals("DISCONNECTED")) {
								Contact c = null;
								try {
									c = cl.findIp(InetAddress.getByName(addr));
								} catch (UnknownHostException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								if (c == null) {
									System.out.println("Ce contact n'etait pas dans la liste de vos contacts.\n");

								} else {
									System.out.println(
											"\n SUPPRESSION DU CONTACT pendant la session " + msg + " " + addr + " \n");
									main.getContactList().removeContact(c);

									// modif of the connected users tab
									String oldusername = c.getPseudo();
									c.delPseudo();
									// if the hostname (of the ip addr) of the contact is in the connected users
									// table we remove it
									if (main.isInTable(c.getPseudo()) != -1)
										main.removeUser(i);

									System.out.println(
											"Modification de la liste des contacts affichés resulat de la modif --> "
													+ main.modUser(c.getPseudo(), main.getDisconnected(), oldusername)
													+ "\n");

									// updating the conversation
									Conversation cv = main.getMessMan().getConv(c);
									if (cv != null) {
										main.getMessMan().removeConv(cv);
									} else if ((cv = main.getMessMan().getStoppedConv(c)) != null) {
										main.getMessMan().removeStoppedConv(cv);
									} else {
										System.out
												.println("You don't have a conversation with the disconnected user.\n");
									}
								}

							} else { // on traite les contacts re�us (soit modif soit nouveau)

								Contact c = null;
								try {
									c = cl.findIp(InetAddress.getByName(addr));
								} catch (UnknownHostException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								if (c != null) {
									System.out.println(
											"\n MODIF D'UN CONTACT pendant un session " + msg + " " + addr + " \n");

									// modifier la liste des contacts
									String oldusername = c.getPseudo();
									c.setPseudo(msg);
									System.out.println("Modification de la liste des contacts"
											+ main.modUser(c.getPseudo(), main.getConnected(), oldusername) + "\n");

								} else {
									c = new Contact(msg, addr);
									System.out.println(
											"\n AJOUT DU CONTACT pendant une session " + msg + " " + addr + " \n");
									cl.addContact(c);
									String hostname = null;
									try {
										hostname = InetAddress.getByName(addr).getHostName();
									} catch (UnknownHostException e) {
										e.printStackTrace();
										new Alert("Error : Please close the program (connecting phase) ! ")
												.setVisible(true);
									}
									if (main.isInTable(hostname) != -1) {
										main.modUser(c.getPseudo(), main.getConnected(), hostname);
									} else {
										main.addUser(c.getPseudo(), main.getConnected());
									}
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

	////////////////// gestion de l'envoi de msg udp
	public void signalDatagram(String m, String ad) {
		System.out.println("Sending : " + m + " to " + ad + " .\n");
		new UDPSender(m, ad).send();
	}

}