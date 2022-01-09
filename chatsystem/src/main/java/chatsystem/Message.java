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
	
	public String toString() {
		return "[From : me " + "]      "+ msg +"      [ Date : "+date +"]";
	}
}
