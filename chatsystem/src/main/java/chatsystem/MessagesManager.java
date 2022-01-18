package chatsystem;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import userinterface.Alert;
import userinterface.MainMenu;

public class MessagesManager extends Thread { // chaque conversation est g�r� par un Msgsender et un Msgreceiver

	
	private MainMenu main;
	private boolean running;
	private int port = 55555;
	private ServerSocket ss;

	private ArrayList<Conversation> ConvList;
	private ArrayList<Conversation> stoppedConvList;

	private Conversation c = null;
	private Message m = null;

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
			new Alert("Error : Please close the program!\n").setVisible(true);
		}
	}

	public void run() {

		new Thread(new Runnable() {
			@Override
			public void run() {

				Socket doorbell = null;
				while (running) {

					try {
						doorbell = ss.accept();
					} catch (IOException e) {
						e.printStackTrace();
						new Alert("Error : Please close the program!\n").setVisible(true);

					}

					InetAddress host = doorbell.getInetAddress();
					Conversation ec = null;
					for (Conversation withknownhost : ConvList) {
						if (host.equals(withknownhost.getInterlocutor().getIpaddress())) {
							System.out.println("La conversation a �t� trouv�.\n");
							ec = withknownhost;
						}
						
						InetAddress host = doorbell.getInetAddress();
						Contact c = new Contact(host);
						
						Conversation ec = getConv(c);
						
						final Socket sock = doorbell;
						
						if (ec != null) {
							final Conversation encours = ec;

							//We initiated the conversation
							new Thread(new Runnable() {

								@Override
								public void run() {
									//sock is the MsgReceiver socket (a response to our MsgSender socket)
									if (encours.getChatw()==null) {
										encours.startConv(sock);
										System.out.println("A conversation is starting.\n");
									} else {
										encours.reStartConv(sock);
										System.out.println("This conversation was already openned.\n");
									}
								}
									
							}).start();
							
						} else if ((ec=getStoppedConv(c)) != null){
								final Conversation s = ec;
								new Thread(new Runnable() {
								
								@Override
								public void run() {
									//sock is the MsgReceiver socket (a response to our MsgSender socket)
									s.reStartConv(sock);
									System.out.println("We have restarted a conversation.\n");
								}
									
							}).start();
							
						} else {
							
							//The conversation was initiated by one of our contacts
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									
									Contact contact = getMain().getContactList().findIp(host);
									
									if (contact ==null) {
											System.out.println("This person is not in the contacts list !\n");
									} else {
										Conversation cn = new Conversation(main,contact);
										cn.startConv(sock);
										System.out.println("A conversation is accepted.\n");
									
									}
								}
							}

						}).start();
					}
					System.out.println("The server of the messages manager is stopped !\n");
					
				}
			}).start();
			
			//Sending thread (this thread itself)
			while (running) {
				if (c!=null && m!=null)
					sendMessTo(c,m);
			}
			
			for (Conversation c : stoppedConvList) {
				c.getR().setRunning(false);
			}
			
			System.out.println("The sender part of the messages manager is stopped !\n");
		
	}

	public MainMenu getMain() {
		return main;
	}

	public ArrayList<Conversation> getConvList() {
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

	public synchronized void sendMessTo(Conversation c, Message m) {
		if (ConvList.contains(c)) {
			c.getS().send(m);
					System.out.println("A message was sent.\n");
			this.c = null;
			this.m = null;
		} else {
				System.out.println("This conversation is not in the list of on-going conversations.\n");
			this.c = null;
			this.m = null;
		}

	}

	public synchronized void signalMess(Conversation c, Message m) {
			this.c = c;
			this.m = m;
			System.out.println("Preparing the sending of the message...\n");
	}

	public ArrayList<Conversation> getStoppedConvList() {
		return stoppedConvList;
	}

}