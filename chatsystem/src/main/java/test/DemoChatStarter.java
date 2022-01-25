package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DemoChatStarter {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(5098);

			System.out.println("Waiting for connection...");
			while (true) {
				Socket clientSocket;
				clientSocket = serverSocket.accept();
				new TCPServer(clientSocket);
				new TCPClient(clientSocket);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
