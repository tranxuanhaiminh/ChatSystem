package network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import database.Databasecon;
import entities.Conversation;
import entities.Message;
import userinterface.Alert;
import userinterface.MainMenu;

public class MsgReceiver extends Thread {

	private Socket socketreceive;
//	private boolean running;
	private ObjectInputStream in = null;
	private Conversation conv;
	private Message mess;

	public MsgReceiver(Socket sock, Conversation conv) {
		super();
		socketreceive = sock;
		this.conv = conv;
//		this.setRunning(true);
		start();
	}

	public void run() {

//		System.out.println(
//				"The mess receiver starts for the user with the address : " + socketreceive.getInetAddress() + " \n");
//		while (running) {

		try {

			// Set the Input to receive Message
			in = new ObjectInputStream(socketreceive.getInputStream());
			while (true) {

				try {
					mess = (Message) in.readObject();
					if (conv.getChatw() != null) {
						// Displaying the message
						conv.getChatw().addChatLine(mess, false);
					} else {
						// Notify user on the main menu
						MainMenu.notifyMessage(conv.getInterlocutor().getPseudo());
					}
					// adding to the chat history
					Databasecon.insertChat(conv.getInterlocutor().getIpaddress().getCanonicalHostName(),
							conv.getInterlocutor().getPseudo(), mess.toString(), mess.getDate(), false);
				} catch (IOException e) {
					// If the other end has done close output stream
					break;
				}
			}

			// shut down output of the connection on this side
			if (!socketreceive.isOutputShutdown()) {
				socketreceive.shutdownOutput();
			}

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			new Alert("Error : Please close this chat window ! ");
		}
		
//		mess = null;

//		try {
//			socketreceive.close();
//			in.close();
//			System.out.println("Receiver socket closed.\n");
//		} catch (IOException e) {
//			e.printStackTrace();
//			new Alert("Error : Please close this chat window ! ");
//
//		}
	}

}