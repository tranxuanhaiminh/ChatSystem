package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import entities.Message;
import ressources.AlertMessage;
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
			new Alert(AlertMessage.error);
		}
	}

	/* Methods */
	
	/**
	 * Send msg
	 * @param msg
	 */
	public void send(Message msg) throws SocketException {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			new Alert(AlertMessage.error);
		}
	}
	
	/**
	 * Start closing the TCP connection by closing the output stream
	 */
	public void closeSend() {
		try {
			socketsend.shutdownOutput();
		} catch (SocketException e) { 
			// Socket is already shutdown
		} catch (IOException e) {
			e.printStackTrace();
			new Alert(AlertMessage.error);
		}
	}
}