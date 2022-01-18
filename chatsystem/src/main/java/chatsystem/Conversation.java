package chatsystem;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

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
		// fermer la page si elle est ouverte
		if (this.chatw != null) {
			this.chatw.setVisible(false);
			this.chatw.dispose();
			this.chatw = null;
		}

		// supprimer du mess man
		main.getMessMan().getConvList().remove(this);

		// On peut toujours recevoir des messages apr�s avoir ferm� une conversation
		main.getMessMan().getStoppedConvList().add(this);

		s.closeSend();

		System.out.println("Stopping the conversation with " + this.interlocutor + "! \n");

	}

	public synchronized void reStartConv(Socket saccepted) {
		// on crée un nouveau flux d'envoi pour que de l'autre côté reçoive un
		// socket d'aceptation
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

		// on crée un flux de reception vu que l'autre était fermé d'un côté
		this.r.setRunning(false);
		r = new MsgReceiver(saccepted, this);
		r.start();

		// rajouter la conv au mess man si elle n'y était plus
		if (!(main.getMessMan().getConvList().contains(this)))
			main.getMessMan().getConvList().add(this);

		// la supprimer du des conv stoppées si elle y était
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