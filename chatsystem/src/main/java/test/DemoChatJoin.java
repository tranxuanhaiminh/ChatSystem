package test;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoChatJoin {

	static ExecutorService threadpool = Executors.newCachedThreadPool();
	static Socket clientSocket;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String host = "127.0.0.1";
		int port = 5098;
		int port2 = 5099;
		clientSocket = new Socket(host, port);
		threadpool.submit(() -> new TCPClient(clientSocket));
		threadpool.submit(() -> new TCPServer(clientSocket));
		threadpool.shutdown();
		System.out.println("test1");
		System.out.println(clientSocket);
		clientSocket = null;
		System.out.println("test2");
		System.out.println(clientSocket);
	}
}
