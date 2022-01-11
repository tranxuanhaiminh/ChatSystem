package chatsystem;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{
	
	private String msg;
	private Date date;
	private Contact dest;
	
	public Message() {
		this.date = null;
		this.dest = null;
		this.msg = null;
	}
	
	public Message(Contact dest, String msg) {
		this.date = new Date();
		this.dest = dest;
		this.msg = msg;
	}
	
	public String toString() {
		return msg +"      [ Date : "+date +"]";
	}

	public Contact getDest() {
		return dest;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getMsg() {
		return msg;
	}
}
