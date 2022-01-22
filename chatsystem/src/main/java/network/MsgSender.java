package network;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import entities.Message;
import userinterface.Alert;

public class MsgSender /*extends Thread*/{ // on ne doit pas sortir du send sinon �a close la connexion; //ON FERME LE receiver d'abord D4ABORD
	
	private Socket socketsend;
	private ObjectOutputStream out;

	
	public MsgSender(Socket socket) {
		this.socketsend = socket;
		try {
			out = new ObjectOutputStream(socketsend.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert("Error : Please close the program !\n");
		}

	}
	
	public void send(Message msg) {
		
		try {
			if (!socketsend.isOutputShutdown()) {
				out.writeObject(msg);
				out.flush();
			} else {
				System.out.println("Cannot send the msg because the socket output is shutdown.\n");
			}
			
		} catch (IOException e){
			e.printStackTrace();
			new Alert("Error : Please close the program !\n");
		}
	}
	
	public void closeSend() {
		try {
			socketsend.close();
			out.close();
			System.out.println("Sending socket closed\n");
		} catch (IOException e) {
			e.printStackTrace();
			new Alert("Error : Please close the program !\n");
		}
	}
}