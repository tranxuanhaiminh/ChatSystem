package network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import chatsystem.Message;
import userinterface.ChatWindow;

public class MsgReceiver extends Thread{ // Server tcp //client tcp
	
	private Socket socketreceive;
	private boolean running;
	private ObjectInputStream in=null;
	private ChatWindow chatw;
	
	public MsgReceiver(Socket sock, ChatWindow chatw2) throws IOException {
		super();
		this.socketreceive = sock;
		this.chatw = chatw2;
		this.setRunning(true);
		try {
			in = new ObjectInputStream(socketreceive.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.chatw.getProblem().setVisible(true);
		}
	}
	
	public void run() {
		
		System.out.println("Le msg receiver est lancï¿½ pour l'hote : " + socketreceive.getInetAddress()+ " \n");

		Message mess=null;
		
		while (running) {
			
				try {
					mess = (Message) in.readObject();
					System.out.println("MESSAGE RECU = "+ mess+"\n");
					this.chatw.addChatLine(mess,false);
					
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
					this.chatw.getProblem().setVisible(true);
				} catch (EOFException e) {
					//Do Nothing if the end has been reached
					
				} catch (IOException e) {
					e.printStackTrace();
					this.chatw.getProblem().setVisible(true);
				}
				
				mess = null;
			
		}
		
		try {
			socketreceive.close();
			in.close();
			System.out.println("Socket de reception de msg fermé\n");
		} catch (IOException e) {
			e.printStackTrace();
			this.chatw.getProblem().setVisible(true);

		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean go) {
		this.running = go;
	}
	
	public static void main(String[] args) throws IOException {
		
		int port= 55555;
		try (ServerSocket serversock = new ServerSocket(port)) {
			System.out.println("WAITING FOR CONNEXION on port : "+ port+ "\n");
			Socket socketDoorbell = serversock.accept();
			System.out.println("CONNEXION ACCEPTED with : " + socketDoorbell.getInetAddress() +" on port : "+ port+ " Local addr is : "+ socketDoorbell.getLocalAddress()+"\n");
			MsgReceiver m= new MsgReceiver(socketDoorbell,null);
			m.start();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			m.setRunning(false);
		}
	
	}


}
