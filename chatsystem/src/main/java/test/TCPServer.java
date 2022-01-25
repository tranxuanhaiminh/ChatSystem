package test;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TCPServer extends Thread {

	private static Socket clientSocket;
	private String msg;
	static ObjectInputStream input;

	public TCPServer(Socket socket) {
		super();
		clientSocket = socket;
		start();
	}

	public void run() {
		Date date = new java.util.Date();
		PrintWriter out_date;

		try {
			System.out.println("Server Thread started");
			/**
			 * Send Connection time to client
			 */
//			out_date = new PrintWriter(clientSocket.getOutputStream());
//			out_date.println(date);
//			out_date.flush();
//			System.out.println("Server sent connection time");

			/**
			 * Create a variable that wait for messages from clients
			 */
			input = new ObjectInputStream(clientSocket.getInputStream());

			/**
			 * Print message sent from client everytime it's received
			 */
			while (!clientSocket.isOutputShutdown()) {
				try {
					msg = (String) input.readObject();
				} catch (IOException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					break;
				}
				System.out.println("Client send: " + msg);
			}

			/**
			 * Disconnect with the client
			 */
			if (!clientSocket.isOutputShutdown()) {
				clientSocket.shutdownOutput();
			}
			input.close();
			System.out.println("server closed output : " + clientSocket.isOutputShutdown());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("test");
			System.out.println(clientSocket);
			TCPClient.getSock();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getMsg() {
		return this.msg;
	}

	public static void close() {
		try {
			clientSocket.close();
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("server closed!!!");
	}
}
