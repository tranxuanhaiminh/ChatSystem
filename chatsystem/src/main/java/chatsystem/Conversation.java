package chatsystem;

import java.io.IOException;
import java.net.Socket;

import network.MsgReceiver;
import network.MsgSender;
import userinterface.Alert;
import userinterface.ChatWindow;
import userinterface.MainMenu;

public class Conversation {

	private MsgReceiver r;
	private MsgSender s;
	private Contact interlocutor;
	private int port = 55555;
	private MainMenu main;
	private ChatWindow chatw = null;

	public Conversation(MainMenu mm2, Contact i) {
		this.interlocutor = i;
		this.main = mm2;
		try {
			s = new MsgSender(new Socket(i.getIpaddress(), port));
		} catch (IOException e) {
			e.printStackTrace();
			new Alert("Error : Please close the program!\n").setVisible(true);
		}
		main.getMessMan().getConvList().add(this);
	}

	public void startConv(Socket saccepted) {

		this.chatw = new ChatWindow(main, this.interlocutor, this);
		r = new MsgReceiver(saccepted, this);
		r.start();
		System.out.println("Starting the conversation with " + this.interlocutor + "\n");

	}

	public synchronized void stopConv() {
		// if the chat window associated we close it
		if (this.chatw != null) {
//			this.chatw.setVisible(false);
			this.chatw.dispose();
			this.chatw = null;
		}

		//We remove the conversation from the on-going conversations list in the messages manager
		main.getMessMan().getConvList().remove(this);

		//We put the conversation in the stopped conversations list as we can still receive messages
		main.getMessMan().getStoppedConvList().add(this);

		//Closing the sender socket as we no longer want to send messages to this user
		s.closeSend();

		System.out.println("Stopping the conversation with " + this.interlocutor + "! \n");

	}

	public synchronized void reStartConv(Socket saccepted) {
		// we create a new sending flow so that, at the other end, the user's 
		//messages manager can accept the connection and have a receiving socket
		try {
			this.s.closeSend();
			s = new MsgSender(new Socket(this.interlocutor.getIpaddress(), port));
		} catch (IOException e) {
			e.printStackTrace();
			new Alert("Error : Please close the program!\n").setVisible(true);
		}

		if (this.chatw == null) {
			this.chatw = new ChatWindow(main, this.interlocutor, this);
		} else {
			this.chatw.requestFocus();
		}

		//We create a new receiving flow directed to the new sending socket of the interlocutor
		this.r.setRunning(false);
		r = new MsgReceiver(saccepted, this);
		r.start();

		// Adding the conversation in the on-going conversations list if it was not in it
		if (!(main.getMessMan().getConvList().contains(this)))
			main.getMessMan().getConvList().add(this);

		//Removing the ocnversation from the stopped conversations list if it was in it
		if ((main.getMessMan().getStoppedConvList().contains(this)))
			main.getMessMan().getStoppedConvList().remove(this);

		System.out.println("Restarting the conversation with " + this.interlocutor + "! \n");
	}

	public MsgReceiver getR() {
		return r;
	}

	public void setR(MsgReceiver r) {
		this.r = r;
	}

	public MsgSender getS() {
		return s;
	}

	public void setS(MsgSender s) {
		this.s = s;
	}

	public Contact getInterlocutor() {
		return interlocutor;
	}

	public void setInterlocutor(Contact interlocutor) {
		this.interlocutor = interlocutor;
	}

	public ChatWindow getChatw() {
		return chatw;
	}

	public MainMenu getMain() {
		return main;
	}

}