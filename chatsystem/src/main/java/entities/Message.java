package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/* Fields */
	private String msg;
	private Date date;
	private Contact dest;
	
	/* Constructor */
	public Message(Contact dest, String msg) {
		this.date = new Date();
		this.dest = dest;
		this.msg = msg;
	}
	
	/**
	 * No
	 */
	public String toString() {
		return msg +"\n------written at "+getDate();
	}
	
	/**
	 * Get the pseudo of the other end to which this message is going to
	 * @return
	 */
	public String getPseudo() {
		return dest.getPseudo();
	}

	/**
	 * Get the date in the correct format to store in the database
	 * @return
	 */
	public String getDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/* Getters */
	public String getIp() {
		return dest.getIpaddress().getHostAddress();
	}
	
	public String getMsg() {
		return msg;
	}

}