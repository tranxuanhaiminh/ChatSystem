package chatsystem;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import networkconnection.MsgReceiver;
import networkconnection.MsgSender;

public class Conversation {
	
	private MsgReceiver r;
	private MsgSender s;
	private Contact interlocutor;
	private int port = 55555;
	private MainMenu1 mm;
	private ChatWindow1 chatw=null;
	
	public Conversation(MainMenu1 mm,Contact i) {
		this.interlocutor = i;
		this.mm = mm;
		try {
			s = new MsgSender(new Socket(i.getIpaddress(), port));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void startConv(Socket saccepted) {
		this.chatw = new ChatWindow1(mm,this.interlocutor,this);
		r = new MsgReceiver(saccepted, this.chatw);
		r.start();
		System.out.println("CONVERSATION LANCEE avec " + this.interlocutor + "\n");
		
	}
	
	public void stopConv() {
		r.setRunning(false);
		s.closeCo();
		//supprimer du mess man
		mm.getMessMan().getConvList().remove(this);
		System.out.println("Stopping the conversation with "+ this.interlocutor + "! \n");
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
	

}
