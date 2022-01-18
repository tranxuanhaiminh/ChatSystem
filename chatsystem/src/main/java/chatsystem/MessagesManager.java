package chatsystem;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import userinterface.MainMenu;


public class MessagesManager extends Thread{
	
	private MainMenu main;
	private boolean running;
	private int port = 55555;
	private ServerSocket ss; 
	
	private ArrayList<Conversation> ConvList;
	private ArrayList<Conversation> stoppedConvList;
	
	
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
			main.getProblem().display();
		}
	}
	
	
	public void run() {
	
		Socket doorbell=null;
		while (running) {
				
			try {
				doorbell = ss.accept();
			} catch (java.net.BindException e1) {
				e1.printStackTrace();
				main.getProblem().display();
				
			}catch (IOException e) {
				e.printStackTrace();
				main.getProblem().display();

			}
			
			InetAddress host = doorbell.getInetAddress();
			Contact c = new Contact(host);
			
			Conversation ec = getConv(c);
			
			final Socket sock = doorbell;
			
			if (ec != null) {
				final Conversation encours = ec;

				//We initiated the conversation
				new Thread(() -> {
						//sock is the MsgReceiver socket (a response to our MsgSender socket)
						if (encours.getChatw()==null) {
							encours.startConv(sock);
							System.out.println("A conversation is starting.\n");
						} else {
							encours.reStartConv(sock);
							System.out.println("This conversation was already openned.\n");
						}
						
				}).start();
							
				
			} else if ((ec=getStoppedConv(c)) != null){
					final Conversation s = ec;
					
					new Thread(() -> {
						//sock is the MsgReceiver socket (a response to our MsgSender socket)
						s.reStartConv(sock);
						System.out.println("We have restarted a conversation.\n");
						
				}).start();
				
			} else {
				
				//The conversation was initiated by one of our contacts
				new Thread(() -> {
					Contact contact = getMain().getContactList().findIp(host);
					if (contact ==null) {
							System.out.println("This person is not in the contacts list !\n");
					} else {
						Conversation cn = new Conversation(main,contact);
						cn.startConv(sock);
						System.out.println("A conversation is accepted.\n");
					}
					
				}).start();
			}
		}
		System.out.println("The messages manager is stopped !\n");
		
		for (Conversation c : stoppedConvList) {
			c.getR().setRunning(false);
		}
		
	}

	public MainMenu getMain() {
		return main;
	}
	
	public ArrayList<Conversation> getConvList(){
		return this.ConvList;
	}
	
	/* 
	*This method is called when someone disconnects
	*/
	public synchronized void removeConv(Conversation cv) {
		cv.stopConv();
		this.stoppedConvList.remove(cv);
		cv.getR().setRunning(false);
	}
	
	public synchronized void removeStoppedConv(Conversation cv) {
		this.stoppedConvList.remove(cv);
		cv.getR().setRunning(false);
	}
	
	public Conversation getConv(Contact c) {
		Conversation res = null;
		for (Conversation cv : this.ConvList) {
			if (cv.getInterlocutor().equals(c)) {
				res = cv;
			}
		}
		return res;
	}
	
	public Conversation getStoppedConv(Contact c) {
		Conversation res = null;
		for (Conversation cv : this.stoppedConvList) {
			if (cv.getInterlocutor().equals(c)) {
				res = cv;
			}
		}
		return res;
	}
	
	public void setRunning(boolean b) {
		this.running = b;
	}

	public ArrayList<Conversation> getStoppedConvList() {
		return stoppedConvList;
	}
	
	public void sendMessTo(Conversation c, Message m) { //mot cl� synchronized enlev�
			if (ConvList.contains(c)) {
				try {
					c.getS().send(m);
					System.out.println("A message was sent.\n");
				} catch (IOException e) {
					e.printStackTrace();
					main.getProblem().display();
				}
			} else {
				System.out.println("This conversation is not in the list of on-going conversations.\n");
			}
			
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
	
}