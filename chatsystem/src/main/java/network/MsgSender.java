package network;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import chatsystem.Contact;
import chatsystem.Message;

public class MsgSender /*extends Thread*/{ // on ne doit pas sortir du send sinon �a close la connexion; //ON FERME LE receiver d'abord D4ABORD
	
	private Socket socketsend;
	private boolean go;
	private ObjectOutputStream out;
	boolean sent = true;

	
	public MsgSender(Socket socket) throws IOException {
		this.socketsend = socket;
		this.setGo(true);
		try {
			out = new ObjectOutputStream(socketsend.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}
	
	public void send(Message msg) throws IOException {
		
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
			throw e;
		}
	}
	
	public void closeSend() throws IOException {
		try {
			socketsend.close();
			out.close();
			System.out.println("Socket d'envoi de messages ferm�\n");
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
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
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*msg = new Message(new Contact("toto",(String)null), "test 2");
		/m.send(msg);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		m.closeSend();
		
	}


}