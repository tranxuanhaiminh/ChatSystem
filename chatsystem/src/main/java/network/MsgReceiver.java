package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import chatsystem.ChatSystem;
import database.Databasecon;
import entities.Conversation;
import entities.Message;
import service.TCPService;
import userinterface.Alert;
import userinterface.MainMenu;

public class MsgReceiver extends Thread {

	/* Fields */
	private Socket socketreceive;
	private ObjectInputStream in = null;
	private Conversation conv;
	private Message message;

	/* Constructor */
	public MsgReceiver(Socket sock, Conversation conv) {
		super();
		socketreceive = sock;
		this.conv = conv;
		start();
	}

	public void run() {

		try {

			// Set the Input to receive Message
			in = new ObjectInputStream(socketreceive.getInputStream());
			while (true) {
				try {
					// Waiting for new message
					message = (Message) in.readObject();
					
					// Handle new message
					ChatSystem.threadpool.submit(() -> TCPService.msgReceive(message, conv));
				} catch (IOException e) {
					// If the other end had closed output stream
					break;
				}
			}

			// shut down output stream on this side
			if (!socketreceive.isOutputShutdown()) {
				socketreceive.shutdownOutput();
			}

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			new Alert("Error : Please close this chat window ! ");
		}
	}

}