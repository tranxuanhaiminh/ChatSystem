package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import database.Databasecon;

public class Dbservice {
	
	/* Fields */
	private static Set<String> pseudolist = new HashSet<String>();
	
	/* Methods */
	
	/**
	 * Start connection with database, create database table and retrieve all usernames in the database if exist
	 * @return
	 */
	public static void dbInit() {
		Databasecon.connect("test");
		Databasecon.createTable();
		retrieveContacts();
	}
	
	public static Set<String> retrieveContacts() {
		ResultSet rs = Databasecon.getContacts();
		try {
			while(rs.next()) {
				String pseudo = null;
				pseudo = rs.getString("senderpseudo");
				if (pseudo != null) {
					pseudolist.add(pseudo);
				}
				pseudo = rs.getString("receiverpseudo");
				if (pseudo != null) {
					pseudolist.add(pseudo);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pseudolist;
	}
	
	/* Getters */
	public static Set<String> getpseudolist() {
		return pseudolist;
	}
}
