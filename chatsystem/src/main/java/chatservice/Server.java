// package chatservice;

import java.net.*;
import java.io.*;
import java.util.*;

class ServerThread extends Thread {
	final Socket clientSocket;
	ServerThread(Socket clientSocket) {
		super();
		this.clientSocket = clientSocket;
//		start();
	}
	public void run() {
		BufferedReader input;
		String msg;
		Date date = new java.util.Date();
		PrintWriter out_date;
		
		try {			
			/**
			 * Send Connection time to client
			 */
			out_date = new PrintWriter(clientSocket.getOutputStream());
			out_date.println(date);
			out_date.flush();
			System.out.println("Connection Successful");
			
			/**
			 * Create a variable that wait for messages from clients
			 */
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			msg = input.readLine();
			
			/**
			 * Print message sent from client everytime it's received
			 */
			while(msg != null) {
				System.out.println("Client send: " + msg);
				msg = input.readLine();
			}
			
			/**
			 * Disconnect with the client
			 */
			clientSocket.close();
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class IServerThread implements Runnable {
	final Socket clientSocket;
	
//	Thread thread;
	
	IServerThread (Socket clientSocket) {
		this.clientSocket = clientSocket;
//		thread = new Thread(this);
//		thread.start();
	}
	
	public void run() {
		BufferedReader input;
		String msg;
		Date date = new java.util.Date();
		PrintWriter out_date;
		
		System.out.println("Thread started");
		
		try {			
			/**
			 * Send Connection time to client
			 */
			out_date = new PrintWriter(clientSocket.getOutputStream());
			out_date.println(date);
			out_date.flush();
			System.out.println("Connection Successful");
			
			/**
			 * Create a variable that wait for messages from clients
			 */
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			msg = input.readLine();
			
			/**
			 * Print message sent from client everytime it's received
			 */
			while(msg != null) {
				System.out.println("Client send: " + msg);
				msg = input.readLine();
			}
			
			/**
			 * Disconnect with the client
			 */
			clientSocket.close();
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public class Server {
		
	public static void main(String args[]) {
		try {
			ServerSocket serverSocket = new ServerSocket(5000);

			System.out.println("Waiting for connection..." + InetAddress.getByName("255.255.255.255"));
			while(true) {
				Socket clientSocket = serverSocket.accept();
				
				// Start server thread using serverthread subclass
//				new ServerThread(clientSocket).start();
				
				// Start serverthread using serverthread interface
//				new IServerThread(clientSocket);
				new Thread(new IServerThread(clientSocket)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
