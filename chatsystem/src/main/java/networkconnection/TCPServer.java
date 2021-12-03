package networkconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TCPServer extends Thread {
	final Socket clientSocket;
	public TCPServer(Socket clientSocket) {
		super();
		this.clientSocket = clientSocket;
		start();
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
