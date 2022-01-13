package network;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import chatsystem.Contact;
import chatsystem.Message;

public class MsgSender /*extends Thread*/{ // on ne doit pas sortir du send sinon �a close la connexion; //ON FERME LE receiver d'abord D4ABORD
	
	private Socket socketsend;
	private Message msg=null;
	private boolean go;
	private int port = 55555;
	private ObjectOutputStream out;
	boolean sent = true;

	
	public MsgSender(Socket socket) {
		//super();
		this.socketsend = socket;
		this.setGo(true);
		try {
			out = new ObjectOutputStream(socketsend.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void send(Message msg) {
		
		try {
			if (!socketsend.isOutputShutdown()) {
				System.out.println("J'envoie un msg\n");
				out.writeObject(msg);
				out.flush();
				sent = true;
			}
			else {
				System.out.println("The socket output is shutdown\n");
			}
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void closeCo() {
		try {
			socketsend.close();
			out.close();
			System.out.println("Socket d'envoi de messages ferm�\n");
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
		MsgSender m= new MsgSender(clientSocket);
		
		Message msg = new Message(new Contact("toto",(String)null), "test 1");
		m.send(msg);		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = new Message(new Contact("toto",(String)null), "test 2");
		m.send(msg);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		m.closeCo();
		
	}


}


