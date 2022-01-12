package chatsystem;

import java.io.Serializable;

public class Contact implements Serializable{
	
	private String pseudo;
	private String ipaddress; // � convertir en InetAddress
	
	
	public Contact() {
		this.pseudo = null;
		this.ipaddress = null;
	}
	
	public Contact(String p, String ip) {
		this.pseudo = p;
		this.ipaddress = ip;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getIpaddress() {
		return ipaddress;
	}
	
	public String toString() {
		return this.pseudo;
	}
	
}
