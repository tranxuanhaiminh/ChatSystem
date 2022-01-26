package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import chatsystem.ChatSystem;
import entities.ContactList;
import entities.Conversation;
import entities.ConversationList;
import service.TCPService;

public class TCPListener extends Thread {

	/* Fields */
	private static int port = 55555;

	/* Constructor */
	public TCPListener() {
		super();
		start();
	}

	public void run() {
		
		// Try-with-ressources statement
		try (ServerSocket socketListener = new ServerSocket(port)) {
			while (true) {
				
				// Wait for new TCP connection
				Socket socket = socketListener.accept();
				
				// Handle new TCP connection
				ChatSystem.threadpool.submit(() -> TCPService.newTCP(socket));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
