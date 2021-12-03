package networkconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient extends Thread {
	public TCPClient() {
		super();
		start();
	}
	public void run() {
		Socket clientSocket;
		String host = "127.0.0.1";
		int port = 5000;
		Scanner s = new Scanner(System.in);
		PrintWriter out;
		String msg;
		BufferedReader input;
		
		try {
			/**
			 * Create the connection on the corresponding port
			 */
			clientSocket = new Socket(host,port);
			
			/**
			 * Receive the connection time from the server
			 */
			System.out.println("Waiting for connection");
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			msg = input.readLine();
			System.out.println("Connection Successful on " + msg);
			
			/**
			 * Create a variable to send message to the server
			 */
			out = new PrintWriter(clientSocket.getOutputStream());
			
			/**
			 * Send message to the server everytime the client enter a message
			 */
			msg = s.nextLine();
			while(!msg.equals("exit")) {
				out.println(msg);
				out.flush();
				msg = s.nextLine();
			}
			
			/**
			 * Close the scanner and the message receiving variable
			 */
			s.close();
			out.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}