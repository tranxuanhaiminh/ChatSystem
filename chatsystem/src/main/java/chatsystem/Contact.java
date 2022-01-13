package chatsystem;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Contact implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pseudo;
	private InetAddress ipaddress;
	
	
	public Contact() {
		this.pseudo = null;
		this.ipaddress = null;
	}
	
	public Contact(String p, InetAddress ip) {
		this.pseudo = p;
		this.ipaddress = ip;
	}
	
	public Contact(InetAddress ip) {
		this.pseudo = ip.getHostName();
		this.ipaddress=ip;
	}
	
	public Contact(String p, String ip) {
		this.pseudo = p;
		try {
			this.ipaddress = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public InetAddress getIpaddress() {
		return ipaddress;
	}
	
	public String toString() {
		return this.pseudo;
	}
	
	public String ContactToString() {
		return this.pseudo + "("+this.ipaddress+")";
	}
	
	// Two Contacts are equals if they have the same IPaddress
	public boolean equals(Contact c) {
		if (this.ipaddress.equals(c.getIpaddress())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		Contact c=null;
		Contact t=null;
		try {
			c=new Contact("titi",InetAddress.getLocalHost());
			t = new Contact("toto", InetAddress.getByName("LaptopMari�tou"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println(c.getIpaddress()+" "+ t.getIpaddress());
		System.out.println(c.getIpaddress().equals(t.getIpaddress()));
		
	}
	
}
