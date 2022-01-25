package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPListener extends Thread{
	
	/* Fields */
	private static ServerSocket socketListener;
	private Socket socket;
	private static int port = 55555;

	/* Constructor */
	public TCPListener() {
		super();
		try {
			socketListener = new ServerSocket(port);
			start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			try {
				socket = socketListener.accept();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
