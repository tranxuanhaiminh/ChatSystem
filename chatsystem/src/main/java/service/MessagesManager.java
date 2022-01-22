package service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import entities.Contact;
import entities.ContactList;
import entities.Conversation;
import entities.Message;
import userinterface.Alert;

public class MessagesManager extends Thread {

	private boolean running;
	private int port = 55555;
	private ServerSocket ss;

	private static ArrayList<Conversation> ConvList; // on-going conversations
	private static ArrayList<Conversation> stoppedConvList; //stopped conversation
	
	public MessagesManager() {
		super();
		running = true;
		ConvList = new ArrayList<Conversation>();
		stoppedConvList = new ArrayList<Conversation>();

		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			new Alert("Error : Please close the program!");
		}
		start();
	}

	public void run() {
	
		Socket doorbell=null;
		while (running) {
			
			try {
				doorbell = ss.accept();
			} catch (IOException e) {
				e.printStackTrace();
				new Alert("Error : Please close the program!");

			}
			
			InetAddress host = doorbell.getInetAddress();
			Contact c = new Contact(host);
			
			final Socket sock = doorbell;
			
			Conversation ec = getConv(c);
			
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
						s.reStartConv(sock);
						System.out.println("We have restarted a conversation.\n");
						
				}).start();
				
			} else {
				
				//The conversation was initiated by one of our contacts
				new Thread(() -> {
					Contact contact = ContactList.findIp(host);
					if (contact ==null) {
							System.out.println("This person is not in the contacts list !\n");
					} else {
						Conversation cn = new Conversation(contact);
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

	public static ArrayList<Conversation> getConvList() {
		return ConvList;
	}

	/*
	 * This method is called when someone we were having an on-going conversation with disconnects
	 */
	public static synchronized void removeConv(Conversation cv) {
		cv.stopConv();
		stoppedConvList.remove(cv);
		cv.getR().setRunning(false);
	}
	
	/*
	 * This method is called when someone we were having a stopped conversation with disconnects
	 */
	public static synchronized void removeStoppedConv(Conversation cv) {
		stoppedConvList.remove(cv);
		cv.getR().setRunning(false);
	}

	public static Conversation getConv(Contact c) {
		Conversation res = null;
		for (Conversation cv : ConvList) {
			if (cv.getInterlocutor().isEquals(c)) {
				res = cv;
			}
		}
		return res;
	}

	public static Conversation getStoppedConv(Contact c) {
		Conversation res = null;
		for (Conversation cv : stoppedConvList) {
			if (cv.getInterlocutor().isEquals(c)) {
				res = cv;
			}
		}
		return res;
	}

	public static ArrayList<Conversation> getStoppedConvList() {
		return stoppedConvList;
	}
	
	//To send a message from the Action class
	public static void sendMessTo(Conversation c, Message m) {
			if (ConvList.contains(c)) {
				c.getS().send(m);
				System.out.println("A message was sent.\n");
			} else {
				System.out.println("This conversation is not in the list of on-going conversations.\n");
			}
			
	}
}