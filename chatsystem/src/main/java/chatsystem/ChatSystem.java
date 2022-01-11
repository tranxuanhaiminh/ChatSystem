package chatsystem;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.util.ArrayList;

import database.Databasecon;
import network.IpAddress;

import java.sql.*;

public class ChatSystem {


	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		IpAddress ipaddress = new IpAddress();
		Databasecon dbcon = new Databasecon();
		InetAddress ipadd = null;
		String dbfile = "test.db";
		
		// Get the IP address of this machine (First valid one, not treated if many) 
		ipadd = ipaddress.getAddress();
		System.out.println(ipadd.toString());
		
		// Connect to the database from the databasefile name
		java.sql.Connection conn = dbcon.connect(dbfile);
		
		
		if (dbcon.checktable(conn, "chatHistory")) {
			System.out.println("Database table already exists");
		} else {
			if (dbcon.createTable(conn)) {
				System.out.println("Database table successfully created");
			} else {
				System.out.println("Failed, database table was not created");
			}
		}
	}

}
