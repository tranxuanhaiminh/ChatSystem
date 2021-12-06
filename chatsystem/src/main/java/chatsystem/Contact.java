package chatsystem;

public class Contact {
	
	private String pseudo;
	private String ipaddress;
	
	
	public Contact() {
		this.pseudo = null;
		this.ipaddress = null;
	}
	
	public Contact(String p, String ip) {
		this.pseudo = p;
		this.ipaddress = ip;
	}
	
	public Contact(String p) {
		this.pseudo = p;
		this.ipaddress = null;
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
	
}
