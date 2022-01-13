package chatsystem;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import network.MsgReceiver;
import network.MsgSender;
import userinterface.ChatWindow;
import userinterface.MainMenu;

public class Conversation {
	
	private MsgReceiver r;
	private MsgSender s;
	private Contact interlocutor;
	private int port = 55555;
	private MainMenu main;
	private ChatWindow chatw=null;
	
	public Conversation(MainMenu mm2,Contact i) {
		this.interlocutor = i;
		this.main = mm2;
		try {
			s = new MsgSender(new Socket(i.getIpaddress(), port));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			main.getProblem().setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
			main.getProblem().setVisible(true);
		}
		main.getMessMan().getConvList().add(this);
		
	}
	
	public void startConv(Socket saccepted) {
		
		this.chatw = new ChatWindow(main,this.interlocutor,this);
		
		try {
			r = new MsgReceiver(saccepted, this.chatw);
			
		} catch (IOException e) {
			e.printStackTrace();
			main.getProblem().setVisible(true);

		}
		
		r.start();
		System.out.println("CONVERSATION LANCEE avec " + this.interlocutor + "\n");
		
		System.out.println(main.getMessMan().getConvList());
	}
	
	public void stopConv() {
		//supprimer du mess man
		main.getMessMan().getConvList().remove(this);
				
		//r.setRunning(false); On peut toujours recevoir des messages après avoir fermé une conversation
		main.getMessMan().getStoppedConvList().add(this);
		
		try {
			s.closeSend();
		} catch (IOException e) {
			e.printStackTrace();
			main.getProblem().setVisible(true);
		}
		
		System.out.println("Stopping the conversation with "+ this.interlocutor + "! \n");
		System.out.println(main.getMessMan().getConvList());

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
	

}
