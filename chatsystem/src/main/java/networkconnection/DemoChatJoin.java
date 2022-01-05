package networkconnection;

import java.io.IOException;
import java.net.Socket;

public class DemoChatJoin {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String host = "127.0.0.1";
		int port = 5098;
		Socket clientSocket = new Socket(host, port);
		new TCPClient(clientSocket);
		new TCPServer(clientSocket);
	}
}
