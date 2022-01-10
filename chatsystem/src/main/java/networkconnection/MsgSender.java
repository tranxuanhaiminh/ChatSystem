package networkconnection;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import chatsystem.Contact;
import chatsystem.Message;

public class MsgSender extends Thread{ // on ne doit pas sortir du send sinon ça close la connexion; //ON FERME LE receiver d'abord D4ABORD
	
	private Socket socketsend;
	private Message msg=null;
	private boolean go;
	private int port = 55555;
	private ObjectOutputStream out;
	boolean sent = true;

	
	public MsgSender(Socket socket) {
		super();
		this.socketsend = socket;
		this.setGo(true);
		out=null;
	}
	
	public void run() {
		
		System.out.println("Le msg sender est lancé pour l'hote : " + socketsend.getInetAddress()+ " \n");
		
		while (go) {

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (msg != null) {
				System.out.println("J'envoie un msg\n");
				try {
					if (!socketsend.isOutputShutdown()) {
						out = new ObjectOutputStream(socketsend.getOutputStream());
						out.writeObject(msg);
						sent = true;
					}
					
				} catch (IOException e){
					e.printStackTrace();
				}
			}
			msg = null;
			
		}
		
		try {
			socketsend.close();
			out.close();
			System.out.println("Socket d'envoi de messages fermé\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setMsg(Message mess) {
		while(!sent) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("the msg is set\n");
		this.msg = mess;
		sent = false;
	}
	

	public boolean isGo() {
		return go;
	}

	public void setGo(boolean go) {
		this.go = go;
	}
	
	public Socket getSocketsend() {
		return socketsend;
	}

	public static void main(String[] args) throws IOException {
		
		String host = InetAddress.getLocalHost().getHostName();
		int port = 55555;
		Socket clientSocket = new Socket(host, port);
		Message msg = new Message(new Contact("toto"), "test 1");
		MsgSender m= new MsgSender(clientSocket);
		m.start();
		
		m.setMsg(msg);
		
		msg = new Message(new Contact("toto"), "test 2");
		
		m.setMsg(msg);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.setGo(false);
		
	}


}


