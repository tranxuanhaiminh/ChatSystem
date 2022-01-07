package chatsystem;
import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{
	
	private String msg;
	private Date date;
	private Contact dest;
	
	
	public Message(Contact dest, String msg) {
		this.date = new Date();
		this.dest = dest;
		this.msg = msg;
	}
	
	public void printMsg(ChatWindow1 c) {
		if (c == null) {
			System.out.println(msg + " Date : "+ date.toString());
		}
		
	}
	
	public String toString() {
		return msg +"\nDate : "+date+"";
	}
}
