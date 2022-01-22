package network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import database.Databasecon;
import entities.Conversation;
import entities.Message;
import userinterface.Alert;


public class MsgReceiver extends Thread{
	
	private Socket socketreceive;
	private boolean running;
	private ObjectInputStream in = null;
	private Conversation conv;

	public MsgReceiver(Socket sock, Conversation conv) {
		super();
		socketreceive = sock;
		this.conv = conv;
		this.setRunning(true);
		try {
			in = new ObjectInputStream(socketreceive.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert("Error : Please close this chat window ! ");
		}
	}

	public void run() {

		System.out.println("The mess receiver starts for the user with the address : " 
													+ socketreceive.getInetAddress() + " \n");

		Message mess = null;

		while (running) {

			try {
				mess = (Message) in.readObject();
				System.out.println("Received Message = " + mess + "\n");

				if (conv.getChatw() != null) {
					// displaying the message
					conv.getChatw().addChatLine(mess, false);
				}

				// adding to the chat history
				Databasecon.insertChat(conv.getInterlocutor().getIpaddress().getHostAddress(), conv.getInterlocutor().getPseudo(), mess.toString(),
						mess.getDate(), false);
				System.out.println("Adding the msg sent to you by "
						+ conv.getInterlocutor().getIpaddress().getHostAddress() + " to the chat history\n");

			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				new Alert("Error : Please close this chat window ! ");
			} catch (EOFException e) {
				//Do Nothing if the end of the ObjectInputStrem has been reached
			}catch (IOException e) {
				e.printStackTrace();
				new Alert("Error : Please close this chat window ! ");
			}

			mess = null;

		}

		try {
			socketreceive.close();
			in.close();
			System.out.println("Receiver socket closed.\n");
		} catch (IOException e) {
			e.printStackTrace();
			new Alert("Error : Please close this chat window ! ");

		}
	}

	public void setRunning(boolean go) {
		this.running = go;
	}

}