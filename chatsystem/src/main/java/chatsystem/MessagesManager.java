package chatsystem;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import userinterface.MainMenu;


public class MessagesManager extends Thread{ // chaque conversation est gï¿½rï¿½ par un Msgsender et un Msgreceiver
	
	private MainMenu main;
	private boolean running;
	private int port = 55555;
	private ServerSocket ss; 
	
	private ArrayList<Conversation> ConvList;
	private ArrayList<Conversation> stoppedConvList;
	
	private Conversation c=null;
	private Message m=null;
	
	
	public MessagesManager(MainMenu mainMenu) {
		super();
		this.main = mainMenu;
		this.running = true;
		this.ConvList = new ArrayList<Conversation>();
		this.stoppedConvList = new ArrayList<Conversation>();
		
		try {
			 ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			main.getProblem().setVisible(true);
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
							e1.printStackTrace();
							main.getProblem().setVisible(true);
							
						}catch (IOException e) {
							e.printStackTrace();
							main.getProblem().setVisible(true);

						}
						
						InetAddress host = doorbell.getInetAddress();
						Conversation ec = null;
						for (Conversation withknownhost : ConvList) {
							if (host.equals(withknownhost.getInterlocutor().getIpaddress())) {
								System.out.println("La conversation a été trouvé.\n");
								ec = withknownhost;
							} 
						}
						
						final Conversation encours = ec;
						final Socket sock = doorbell;
						if (encours != null) {
							//create convo (initié par nous)
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									//sock est l'aceptation de notre socket d'envoi
									encours.startConv(sock);
									System.out.println("On a lancé une conversation\n");
								}
									
							}).start();
							
						} else {
							
							//create convo initié par l'autre
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									
									Contact contact = getMain().getContactList().findIp(host);
									
									if (contact ==null) {
											System.out.println("Pas dans la liste de contacts\n");
									} else {
										Conversation cn = new Conversation(main,contact);
										cn.startConv(sock);
										System.out.println("On a accepté une conv\n");
									
									}
								}
								
							}).start();
						}
					}
					System.out.println("Le receiver du messages manager a été arrêté !\n");
					
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
					
					//On arrête les conversations en cours
					for (Conversation c : ConvList) {  
						c.stopConv();
					}
					
					//On arrete tous les recepteurs de messages des conv stoppées
					for (Conversation c : stoppedConvList) {  // en arrêtant le mess man on trop toutes les conv
						c.getR().setRunning(false); //On ne peut plus recevoir de messages
					}
					System.out.println("L'envoyeur du messages manager a été arêté !\n");
				}
				
			}).start();
			
	}


	public MainMenu getMain() {
		return main;
	}
	
	public ArrayList<Conversation> getConvList(){
		return this.ConvList;
	}
	
	/* 
	*Cette fonction est appelée par le ContactsManager lorsqu'un utilisateur se déconnecte 
	* on peut donc tout stopper
	*/
	public synchronized void removeConv(Contact c) {
		for (Conversation cv : this.ConvList) {
			if (cv.getInterlocutor().equals(c)) {
				cv.stopConv();
				this.stoppedConvList.remove(cv);
				cv.getR().setRunning(false);
			}
		}
	}
	
	public void setRunning(boolean b) {
		this.running = b;
	}
	
	public synchronized void sendMessTo(Conversation c, Message m) {
			if (ConvList.contains(c)) {
				try {
					c.getS().send(m);
					System.out.println("Un message a été envoyé.\n");
				} catch (IOException e) {
					e.printStackTrace();
					main.getProblem().setVisible(true);
				}
				this.c = null;
				this.m = null;
			} else {
				System.out.println("la conversation n'est pas répertorié.\n");
				this.c = null;
				this.m = null;
			}
		
	}
	
	public synchronized void signalMess(Conversation c, Message m) {
			this.c = c;
			this.m = m;
			System.out.println("Préparation de l'envoi d'un message\n");
	}
	
	public static void main(String[] args) {
			
			ContactList cl = new ContactList();
			
			try {
				cl.addContact(new Contact("titi",InetAddress.getLocalHost().getHostName()));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			Contact me = new Contact("toto",InetAddress.getLoopbackAddress());
			
			new MainMenu(me, cl, null);
	}


	public ArrayList<Conversation> getStoppedConvList() {
		return stoppedConvList;
	}

}