package networkconnection;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import chatsystem.Message;

public class MsgReceiver extends Thread{ // Server tcp //client tcp
	
	private Socket socketreceive;
	private boolean go;
	private ObjectInputStream in=null;
	
	public MsgReceiver(Socket sock) {
		super();
		this.socketreceive = sock;
		this.setGo(true);
		try {
			in = new ObjectInputStream(socketreceive.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		
		System.out.println("Le msg receiver est lancé pour l'hote : " + socketreceive.getInetAddress()+ " \n");

		Message mess=null;
		
		while (go) {
			
				//System.out.println("J'attends un msg \n");
				
				try {
					mess = (Message) in.readObject();
					System.out.println("MESSAGE RECU = "+ mess+"\n");
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EOFException e) {
					//Do Nothing if the end has been reached
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mess = null;
			
		}
		
		try {
			socketreceive.close();
			in.close();
			System.out.println("Socket de reception de msg fermé\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isGo() {
		return go;
	}

	public void setGo(boolean go) {
		this.go = go;
	}
	
	public static void main(String[] args) throws IOException {
		
		int port= 55555;
		ServerSocket serversock = new ServerSocket(port);
		System.out.println("WAITING FOR CONNEXION on port : "+ port+ "\n");
		Socket socketDoorbell = serversock.accept();
		System.out.println("CONNEXION ACCEPTED with : " + socketDoorbell.getInetAddress() +" on port : "+ port+ " Local addr is : "+ socketDoorbell.getLocalAddress()+"\n");
		MsgReceiver m= new MsgReceiver(socketDoorbell);
		m.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.setGo(false);
	
	}


}
