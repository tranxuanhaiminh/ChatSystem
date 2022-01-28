package entities;

import java.io.Serializable;
import java.net.InetAddress;

import network.IpAddress;
import userinterface.MainMenu;

public class Contact implements Serializable {

	/* Fields */
	private static final long serialVersionUID = 1L;
	private String pseudo;
	private InetAddress ipaddress;

	/* Constructor */
	public Contact(String p) {
		pseudo = p;
		ipaddress = IpAddress.getIp();
	}
	
	public Contact(String p, InetAddress ip) {
		pseudo = p;
		ipaddress = ip;
	}

	/* Methods */

	/**
	 * Change pseudo in the list and in the main menu frame
	 * @param psd
	 */
	public void setPseudo(String psd) {
		try {
			MainMenu.modUser(pseudo, psd, true);
		} catch (NullPointerException e) {}
		pseudo = psd;
	}

	/* Getters */
	public String getPseudo() {
		return pseudo;
	}

	public InetAddress getIpaddress() {
		return ipaddress;
	}
}