package chatsystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPReceiver{
	
	private boolean running;
	private DatagramSocket receiversocket;
	private int port;
	private byte[] buffer;
	private DatagramPacket in;
	
	public UDPReceiver(){
		this.port = 58799;
		this.running=true;
		try {
			this.receiversocket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.out.println("Erreur lors de la creation du socket de reception \n");
		}

		try {
			receiversocket.setBroadcast(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public void run(){
		
		while (running) {

			
			
			if (msg.equals("ASK"){
				System.out.println("RECEIVED : "+msg +" FROM "+ in.getAddress().getHostName()+"\n");
				UDPSender s = new UPDSender()
			}
			//System.out.println("RECEIVED : "+msg +" FROM "+ in.getAddress().getHostName()+"\n");
		}
		
		receiversocket.close();
		
	}*/
	
	public String receive() {
		
		System.out.println("Pret à recevoir \n");
		buffer = new byte[256];
		in = new DatagramPacket(buffer, buffer.length);
		
		try {
			receiversocket.receive(in);
		} catch (IOException e) {
			System.out.println("Erreur lors de la reception du mess\n");
		}
		
		String msg = new String(in.getData(),0,in.getLength());
		
		//recuperer l'add et la retourner avec le msg
		
		return msg;
	}
	
	
	public void setRunning (boolean b) {
		this.running= b;
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
}
