package database;

import java.sql.*;

import ressources.Databasequerries;

public class Databasecon {

	// Database location (in main folder)
	private static String url = "jdbc:sqlite:";
	private static Connection c;


	/*
	 * Methods
	 */
	
	/**
	 * Connect to the database from the databasefile name (dbfile)
	 * @param dbfile
	 * @return void
	 */
	public static void connect(String dbfile) {
		try {
			c = DriverManager.getConnection(url + dbfile);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Check if database table with specified name existed in the database
	 * @param String
	 * @return boolean
	 */
	public static boolean checktable(String table) {
		DatabaseMetaData md;
		try {
			md = c.getMetaData();
			ResultSet rs = md.getTables(null, null, table, null);
			return rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Create database table
	 */
	public static void createTable() {
		String sql = Databasequerries.createTable;

		try {
			Statement stmt = c.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get the sender of the line with specified id
	 * @param int
	 * @return String
	 */
	public static String getSender(int id) {
		String sql = Databasequerries.getSender;
		PreparedStatement pstmt;
		try {
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getString("sender");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get the receiver of the line with specified id
	 * @param int
	 * @return String
	 */
	public static String getReceiver(int id) {
		String sql = Databasequerries.getReceiver;
		PreparedStatement pstmt;
		try {
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getString("receiver");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get the chatline with specified id
	 * @param int
	 * @return String
	 */
	public static String getChatLine(int id) {
		String sql = Databasequerries.getChatLine;
		PreparedStatement pstmt;
		try {
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getString("sentChat");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get the sent time of the line with specified id
	 * @param int
	 * @return String
	 */
	public static String getSentTime(int id) {
		String sql = Databasequerries.getSentTime;
		PreparedStatement pstmt;
		try {
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getString("createdDate");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Insert chat to database
	 * @param person
	 * @param pseudo
	 * @param chatline
	 * @param time
	 * @param sent
	 */
	public static void insertChat(String person, String pseudo, String chatline, String time, boolean sent) {
		String sql = null;
		if (sent) {
			sql = Databasequerries.insertChat1;
		} else {
			sql = Databasequerries.insertChat2;
		}
		try {
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, person);
			pstmt.setString(2, pseudo);
			pstmt.setString(3, chatline);
			pstmt.setString(4, time);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get [limit] number of chat line in history with the most recent datetime starting from the [offset]th chat line
	 */
	public static ResultSet getChatHistory(String person, int limit, int offset) {
		String sql = Databasequerries.getChatHistory;
		try {
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, person);
			pstmt.setString(2, person);
			pstmt.setInt(3, limit);
			pstmt.setInt(4, offset);
			return pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * Get all sender and receiver from database
	 * @return ResultSet
	 */
	public static ResultSet getContacts() {
		String sql = Databasequerries.getContacts;
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Get the database connection
	 * @return Connection
	 */
	public static Connection getC() {
		return c;
	}
}
