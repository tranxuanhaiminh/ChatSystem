package networkconnection;


import java.net.Socket;

import chatsystem.Contact;
import chatsystem.Message;

public class MsgSender extends Thread { //s'occupe des sessions démarrées par un interlocuteur
	
	private Socket clientSocket;
	private Message msg;
	
	public MsgSender(Socket clientSocket, Message msg) {
		super();
		this.clientSocket = clientSocket;
		this.msg = msg;
		
		//� starter
	}
	
	public void run() {
		
		BufferedReader input;
		
		try {		
			System.out.println("Client Thread started");	
			/**
			 * Receive the connection time from the server
			 */
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//			
			msg = input.readLine();
//			System.out.println("Connection Successful on " + msg);
			
			/**
			 * Create a variable to send message to the server
			 */
			out = new PrintWriter(clientSocket.getOutputStream());
			System.out.println("Client ready to send message");
			
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


}
