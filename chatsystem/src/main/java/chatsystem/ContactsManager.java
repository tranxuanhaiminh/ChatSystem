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
	private boolean state; // false = connecting phase true = Main phase
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
			//Looks like we need a sleep if we don't want an infinite loop
			try {
				Thread.sleep(1);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
				new Alert("Error : Please close the program (connecting phase) ! ").setVisible(true);
			}
			
			int i = 0;

			while (this.isRunning()) {
				i++;

				///////////////////////////Connection phase

				if (state == false && i == 1) {

					new UDPSender("ASK").send();
					System.out.println("Sending the contacts request.\n");

					System.out.println("Starting the contacts reception during the connecting pahse.\n");

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
						response = ContactReceiver.receive(); // this is a blocking method

						if (response != null) {

							String msg = response[0];
							String addr = response[1];
							ContactList cl = this.cc.getContactList();

							//Threating the messages received
							if ((msg.equals("ASK"))) {

								// We don't do anything because we don't have a username yet

							} else if (msg.equals("DISCONNECTED")) {

								Contact c = null;
								try {
									c = this.cc.getContactList().findIp(InetAddress.getByName(addr));
								} catch (UnknownHostException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									new Alert("Error : Please close the program (connecting phase) ! ").setVisible(true);
								}

								if (c != null) {
									c.delPseudo();
									this.cc.getContactList().removeContact(c);
									System.out.println(
											"A contact have disconnected in the connecting phase !\n");
								}

							} else {

								// To avoid adding twince the same person if they changed their 
								//username during the connecting phase
								Contact cin = null;
								try {
									cin = this.cc.getContactList().findIp(InetAddress.getByName(addr));
								} catch (UnknownHostException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									new Alert("Error : Please close the program (connecting phase) ! ").setVisible(true);
								}

								if (cin == null) {
									System.out.println("ADDING A CONTACT from the connecting phase " + addr + " "
											+ msg + " \n");
									cl.addContact(new Contact(msg, addr));
								} else {
									System.out.println(cin.getIpaddress()
											+ " has modified his username during the connecting phase ! New username = "
											+ msg + " \n");
									cin.setPseudo(msg);
								}
							}
						} else {
							System.out.println("We've received null.\n");
						}

					}

					this.ContactReceiver.setRunning(false);
					System.out.println("End of the reception of contacts in the connecting phase.\n");
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

					/////////////////////////Main phase

					(new UDPSender(this.cc.getMe().getPseudo())).send();
					System.out.println("SENDING our contact to everybody !\n");

					MainMenu main = this.cc.getMain();
					while (main == null) {
						try {
							Thread.sleep(1); // We wait so that we don't have an infinite loop
						} catch (InterruptedException err) {
							err.printStackTrace();
							new Alert("Error : Please close the program (connecting phase) !").setVisible(true);
						}
						main = this.cc.getMain();
					}

					System.out.println("Starting the receiver in the main pahse\n");
					this.ContactReceiver.setRunning(true);

					while (this.isRunning()) {

						///////////////////////////// Processing the reception of contacts
						String[] response = null;
						response = ContactReceiver.receive();

						if (response != null) {
							String msg = response[0];
							String addr = response[1];

							ContactList cl = main.getContactList();

							
							if (msg.equals("ASK")) {

								new UDPSender(main.getMe().getPseudo(), addr).send();
								System.out.println("SENDING our contact to " + addr + "\n");

							} else if (msg.equals("DISCONNECTED")) {
								Contact c = null;
								try {
									c = cl.findIp(InetAddress.getByName(addr));
								} catch (UnknownHostException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									new Alert("Error : Please close the program (main phase) !").setVisible(true);
								}
								
								if (c == null) {
									System.out.println("This disconnected contact was not in the contacts list.\n");

								} else {
									System.out.println(
											"REMOVING the contact : " + msg + " " + addr + " \n");
									main.getContactList().removeContact(c);

									// modifying the connected users' table
									String oldusername = c.getPseudo();
									c.delPseudo();
									// if the hostname (of the ip addr) of the contact is in the connected users'
									// table we remove it
									if (main.isInTable(c.getPseudo()) != -1)
										main.removeUser(i);

									System.out.println(
											"UPDATING the connected users' table. Result --> "
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

							} else { // Processing a new user

								Contact c = null;
								try {
									c = cl.findIp(InetAddress.getByName(addr));
								} catch (UnknownHostException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									new Alert("Error : Please close the program (main phase) !").setVisible(true);
								}

								if (c != null) {
									System.out.println(
											"UPDATING a contact" + msg + " " + addr + " \n");

									// modifier la liste des contacts
									String oldusername = c.getPseudo();
									c.setPseudo(msg);
									System.out.println("Updating the connected users' table. Result -->"
											+ main.modUser(c.getPseudo(), main.getConnected(), oldusername) + "\n");

								} else {
									c = new Contact(msg, addr);
									System.out.println(
											"ADDING a new contact : " + msg + " " + addr + " \n");
									cl.addContact(c);
									
									String hostname = null;
									try {
										hostname = InetAddress.getByName(addr).getHostName();
									} catch (UnknownHostException e) {
										e.printStackTrace();
										new Alert("Error : Please close the program (main phase) ! ").setVisible(true);
									}
									
									//If this user was disconnected, we update his row in the connected users' table 
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

	////////////////Sending an UDP message
	public void signalDatagram(String m, String ad) {
		System.out.println("Sending : " + m + " to " + ad + " .\n");
		new UDPSender(m, ad).send();
	}

}