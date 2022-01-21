package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String msg;
	private Date date;
	private Contact dest;
	
	public Message() {
		this.date = new Date();
		this.dest = null;
		this.msg = null;
	}
	
	public Message(Contact dest, String msg) {
		this.date = new Date();
		this.dest = dest;
		this.msg = msg;
	}
	
	public String toString() {
		return msg +"\n------written at "+getDate();
	}
	
	public String getPseudo() {
		return dest.getPseudo();
	}
	
	public String getIp() {
		return dest.getIpaddress().getCanonicalHostName();
	}
	
	public String getMsg() {
		return msg;
	}
	
	public String getDate(){
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    return sdf.format(date);
	}

}