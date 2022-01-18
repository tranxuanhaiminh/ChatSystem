package network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import chatsystem.Conversation;
import chatsystem.Message;
import database.Databasecon;
import userinterface.Alert;
import userinterface.ChatWindow;

public class MsgReceiver extends Thread { // Server tcp //client tcp

	private Socket socketreceive;
	private boolean running;
	private ObjectInputStream in = null;
	private Conversation conv;

	public MsgReceiver(Socket sock, Conversation conv) {
		super();
		this.socketreceive = sock;
		this.conv = conv;
		this.setRunning(true);
		try {
			in = new ObjectInputStream(socketreceive.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert("Error : Please close this chat window ! ").setVisible(true);
		}
	}

	public void run() {

		System.out.println("Le msg receiver est lanc� pour l'hote : " + socketreceive.getInetAddress() + " \n");

		Message mess = null;

		while (running) {

			try {
				mess = (Message) in.readObject();
				System.out.println("MESSAGE RECU = " + mess + "\n");

				if (conv.getChatw() != null) {
					// displaying the msg
					conv.getChatw().addChatLine(mess, false);
				}

				// adding to the chat history
				conv.getMain().getConDB().insertChat(conv.getInterlocutor().getIpaddress().getHostAddress(),
						mess.toString(), mess.convertDateToFormat(), false);
				System.out.println("Adding the msg sent to you "
						+ conv.getInterlocutor().getIpaddress().getHostAddress() + " to the chat history\n");

			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				new Alert("Error : Please close this chat window ! ").setVisible(true);
			} catch (EOFException e) {
				// Do Nothing if the end has been reached

			} catch (IOException e) {
				e.printStackTrace();
				new Alert("Error : Please close this chat window ! ").setVisible(true);
			}

			mess = null;

		}

		try {
			socketreceive.close();
			in.close();
			System.out.println("Socket de reception de msg ferm�\n");
		} catch (IOException e) {
			e.printStackTrace();
			new Alert("Error : Please close this chat window ! ").setVisible(true);

		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean go) {
		this.running = go;
	}

	public static void main(String[] args) throws IOException {

		int port = 55555;
		try (ServerSocket serversock = new ServerSocket(port)) {
			System.out.println("WAITING FOR CONNEXION on port : " + port + "\n");
			Socket socketDoorbell = serversock.accept();
			System.out.println("CONNEXION ACCEPTED with : " + socketDoorbell.getInetAddress() + " on port : " + port
					+ " Local addr is : " + socketDoorbell.getLocalAddress() + "\n");
			MsgReceiver m = new MsgReceiver(socketDoorbell, null);
			m.start();
			Thread.sleep(10000);

			m.setRunning(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}