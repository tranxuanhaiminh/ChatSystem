package chatsystem;
import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{
	
	/*public enum Type {
		ASK, // demande de contacts
		MSG, // envoi de msg
		ME, // envoi de mon contact
		MODME // demande de modification de mon contact
	};
	
	private Type type;*/
	private String msg;
	private Date date;
	private Contact dest;
	
	/*public Message(Contact dest) {
		this.date = new Date();
		this.type = type;
		this.dest = dest;
	}*/
	
	public Message(Contact dest, String msg) {
		this.date = new Date();
		//this.type = type;
		this.dest = dest;
		this.msg = msg;
	}
	
	public void printMsg(ChatWindow1 c) {
		if (c == null) {
			System.out.println(msg + " Date : "+ date.toString());
		}
		
	}
}
