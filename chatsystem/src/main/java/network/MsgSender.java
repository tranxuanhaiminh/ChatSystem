package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import entities.Message;
import userinterface.Alert;

public class MsgSender {

	/* Fields */
	private Socket socketsend;
	private ObjectOutputStream out;

	/* Constructor */
	public MsgSender(Socket socket) {
		this.socketsend = socket;
		try {
			out = new ObjectOutputStream(socketsend.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Alert("Error : Please close the program !\n");
		}
	}

	/* Methods */
	
	/**
	 * Send msg
	 * @param msg
	 */
	public void send(Message msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			new Alert("Error : Please close the program !\n");
		}
	}
	
	/**
	 * Start closing the TCP connection by closing the output stream
	 */
	public void closeSend() {
		try {
			socketsend.shutdownOutput();
		} catch (IOException e) {
			e.printStackTrace();
			new Alert("Error : Please close the program !\n");
		}
	}
}