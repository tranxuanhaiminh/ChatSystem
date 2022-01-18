package network;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import chatsystem.Contact;
import chatsystem.Message;
import userinterface.Alert;

public class MsgSender /*extends Thread*/{ // on ne doit pas sortir du send sinon �a close la connexion; //ON FERME LE receiver d'abord D4ABORD
	
	private Socket socketsend;
	private boolean go;
	private ObjectOutputStream out;

	
	public MsgSender(Socket socket) {
		this.socketsend = socket;
		this.setGo(true);
		try {
			out = new ObjectOutputStream(socketsend.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert("Error : Please close the program!\n").setVisible(true);
		}

	}
	
	public void send(Message msg) {
		
		try {
			if (!socketsend.isOutputShutdown()) {
				System.out.println("The msg is sent.\n");
				out.writeObject(msg);
				out.flush();
			} else {
				System.out.println("Cannot send the msg because the socket output is shutdown.\n");
			}
			
		} catch (IOException e){
			e.printStackTrace();
			new Alert("Error : Please close the program!\n").setVisible(true);
		}
	}
	
	public void closeSend() {
		try {
			socketsend.close();
			out.close();
			System.out.println("Socket d'envoi de messages ferm�\n");
		} catch (IOException e) {
			e.printStackTrace();
			new Alert("Error : Please close the program!\n").setVisible(true);
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

}