package chatsystem;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class MessagesManager extends Thread{ // chaque conversation est géré par un Msgsender et un Msgreceiver
	
	private MainMenu1 mm;
	private boolean running;
	private int port = 55555;
	private ServerSocket ss;
	
	private ArrayList<InetAddress> talkingHosts;
	
	
	public MessagesManager(MainMenu1 m) {
		super();
		this.mm = m;
		this.running = true;
		this.talkingHosts = new ArrayList<InetAddress>();
		
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
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						try {
							System.out.println(doorbell.getInetAddress()+" "+doorbell.getLocalAddress()+" "+InetAddress.getLocalHost());
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						InetAddress host = doorbell.getLocalAddress();
						boolean in = false;
						for (InetAddress knownhost : talkingHosts) {
							if (host.equals(knownhost)) {
								in = true;
							} 
						}
						
						if (in) {
							//create convo (initié par nous)
							new Thread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									try {
										if (doorbell.getInetAddress().equals(InetAddress.getByName(this.interloc.getIpaddress()))) {
											this.chat.startConv(doorbell);
										} else {
											System.out.println("Pas cette convo\n");
										}
										
									} catch (UnknownHostException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									System.out.println("On a lancé une conv\n");
								}
								
								
								
							});
							
						} else {
							talkingHosts.add(host);
							
							//create convo initié par nous
							new Thread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
								}
								
							});
						}
					}
					System.out.println("Le receiver du messages manager a été arrété !\n");
					
				}
			});
			
			//Thread d'envoi
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (running) {
					}
				}
			});
			
	}
	

	public MainMenu1 getMm() {
		return mm;
	}
	
	
	public static void main(String[] args) {
			
			ContactList cl = new ContactList();
			
			cl.addContact(new Contact("titi","LaptopMariétou"));
	
			Contact me = new Contact("toto","\127.0.0.1");
			ContactsManager cm=null;
			
			MainMenu1 mm= new MainMenu1(me, cl, cm);
			
	}
	
	

}

class Task implements Runnable{
	
	private Socket doorbell;
	private boolean startConv;
	private Contact interloc;
	private MessagesManager messm;
	private Conversation chat;
	
	public Task(MessagesManager messm, Socket s, boolean sess, Contact i, Conversation c) {
		super();
		this.messm = messm;
		this.doorbell = s;
		this.startConv = sess;
		this.interloc = i;
		this.chat = c;
	}
	
	
	public void run() {
	
		if (startConv) { // chat starter
			try {
				//Conversation c = new Conversation(this.interloc);
				if (doorbell.getInetAddress().equals(InetAddress.getByName(this.interloc.getIpaddress()))) {
					this.chat.startConv(doorbell);
				} else {
					System.out.println("Pas cette convo\n");
				}
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("On a lancé une conv\n");
	
		} else {
		
			Contact contact = messm.getMm().getContactList().exists(doorbell.getInetAddress().getHostName());
			
			if (contact ==null)
				contact = messm.getMm().getContactList().exists(doorbell.getInetAddress().toString());
			
			if (contact ==null) {
					System.out.println("Pas dans la liste de contacts\n");
			} else {
				Conversation cn = new Conversation(contact);
				cn.startConv(doorbell);
				System.out.println("On a accepté une conv\n");
			}
			
		}
	}
	
	
	

}
