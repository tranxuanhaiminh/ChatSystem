package service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import database.Databasecon;
import entities.Contact;
import entities.ContactList;

public class DbService {
	
	/* Methods */
	
	/**
	 * Start connection with database, create database table and retrieve all usernames in the database if exist
	 * @return
	 */
	public static void dbInit() {
		Databasecon.connect("test.db");
		Databasecon.createTable();
	}
	
	/**
	 * Get a list of pair of ip and last saved pseudo of offline machine
	 * @return List<Entry<String, String>> ipPseudoList
	 */
	public static void getOfflineContacts() {
		
		// List of iterated ip
		Set<String> ipSet = new HashSet<String>();
		
		// Get contacts from database
		ResultSet rs = Databasecon.getContacts();
		try {
			while(rs.next()) {
				String ip = rs.getString("ip");
				String pseudo = rs.getString("pseudo");
				
				// If the contacts is not in current list then add it to offline list
				if (!ipSet.contains(ip) || (ContactList.findContact(ip) == null)) {
					ipSet.add(ip);
					ContactList.addOffline(new Contact(pseudo, InetAddress.getByName(ip)));
				}
			}
		} catch (NullPointerException e) {
			// Database empty
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
