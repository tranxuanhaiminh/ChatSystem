package chatsystem;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import userinterface.MainMenu;


public class MessagesManager extends Thread{ // chaque conversation est g�r� par un Msgsender et un Msgreceiver
	
	private MainMenu mm;
	private boolean running;
	private int port = 55555;
	private ServerSocket ss;
	
	private ArrayList<Conversation> ConvList;
	
	private Conversation c=null;
	private Message m=null;
	//private Object sending = new Object();
	
	
	public MessagesManager(MainMenu mainMenu) {
		super();
		this.mm = mainMenu;
		this.running = true;
		this.ConvList = new ArrayList<Conversation>();
		
		try {
			 ss = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		
			new Thread(new Runnable() {
				@Override
				public void run() {
					
					Socket doorbell=null;
					int i=0;// pour test
					while (running && i<2) {
						i++;
							
						try {
							doorbell = ss.accept();
						} catch (java.net.BindException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						try {
							System.out.println(doorbell.getInetAddress()+" "+doorbell.getLocalAddress()+" "+InetAddress.getLocalHost());
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						InetAddress host = doorbell.getInetAddress();
						boolean in = false;
						Conversation ec = null;
						for (Conversation withknownhost : ConvList) {
							try {
								if (host.equals(InetAddress.getByName(withknownhost.getInterlocutor().getIpaddress()))) {
									System.out.println("on a trouve la conv\n");
									in = true;
									ec = withknownhost;
								}
							} catch (UnknownHostException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
						}
						System.out.println("sortie ");
						
						final Conversation encours = ec;
						final Socket sock = doorbell;
						if (in) {
							//create convo (initi� par nous)
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									//sock est l'aceptation de notre socket d'envoi
									encours.startConv(sock);
									System.out.println("On a lanc� une conv\n");
								}
									
							}).start();
							
						} else {
							
							//create convo initi� par l'autre
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									Contact contact = getMm().getContactList().exists(host.getHostName());
									
									if (contact ==null)
										contact = getMm().getContactList().exists(host.toString());
									
									if (contact ==null) {
											System.out.println("Pas dans la liste de contacts\n");
									} else {
										Conversation cn = new Conversation(mm,contact);
										ConvList.add(cn);
										cn.startConv(sock);
										System.out.println("On a accept� une conv\n");
									
									}
								}
								
							}).start();
						}
					}
					System.out.println("Le receiver du messages manager a �t� arr�t� !\n");
					
				}
			}).start();
			
			//Thread d'envoi
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					while (running) {
						if (c!=null && m!=null)
						 sendMessTo(c,m);
					}
					
					for (Conversation c : ConvList)  // en arr�tant le mess man on trop toutes les conv
						c.stopConv();
					
					System.out.println("L'envoyeur du messages manager a �t� arr�t� !\n");
				}
				
			}).start();
			
	}


	public MainMenu getMm() {
		return mm;
	}
	
	public ArrayList<Conversation> getConvList(){
		return this.ConvList;
	}
	
	public synchronized void removeConv(Contact c) {
		for (Conversation cv : this.ConvList) {
			if (cv.getInterlocutor().equals(c)) {
				cv.stopConv();
			}
		}
	}
	
	public void setRunning(boolean b) {
		this.running = b;
	}
	
	public synchronized void sendMessTo(Conversation c, Message m) {
		//synchronized (sending) {
			if (ConvList.contains(c)) {
				System.out.println("MESS PRET\n");
				c.getS().send(m);
				this.c = null;
				this.m = null;
			} else {
				System.out.println("la conversation n'est pas r�pertori�\n");
				this.c = null;
				this.m = null;
			}
		//}
		
	}
	
	public synchronized void signalMess(Conversation c, Message m) {
		//synchronized (sending) {
			this.c = c;
			this.m = m;
			System.out.println("MESSAGE EN COURS D ENVOI\n");
		//}
	}
	
	public static void main(String[] args) {
			
			ContactList cl = new ContactList();
			
			try {
				cl.addContact(new Contact("titi",InetAddress.getLocalHost().getHostName()));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Contact me = new Contact("toto","127.0.0.1");
			ContactsManager cm=null;
			
			MainMenu1 mm= new MainMenu1(me, cl, cm);
			
	}
	
	

}