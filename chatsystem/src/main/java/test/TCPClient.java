package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient extends Thread {

	private static Socket clientSocket;
	static Scanner s;
	static ObjectOutputStream out;

	public TCPClient(Socket clientSocket) {
		super();
		this.clientSocket = clientSocket;
		s = new Scanner(System.in);
		start();
	}

	public void run() {

		String msg;
		BufferedReader input;

		try {
			System.out.println("Client Thread started");
			/**
			 * Receive the connection time from the server
			 */
//			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//			msg = input.readLine();
//			System.out.println("Connection Successful on " + msg);

			/**
			 * Create a variable to send message to the server
			 */
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			System.out.println("Client ready to send message");

			/**
			 * Send message to the server everytime the client enter a message
			 */
			while (true) {
				msg = s.nextLine();
				out.writeObject(msg);
				if (msg.equals("exit")) {
					break;
				}
			}

			/**
			 * Close the scanner and the message receiving variable
			 */
			s.close();
			clientSocket.shutdownOutput();
//			out.close();
			System.out.println("client closed");
			System.out.println("output shutdowned : " + clientSocket.isOutputShutdown());

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getSock() {
		return clientSocket.toString();
	}
}
