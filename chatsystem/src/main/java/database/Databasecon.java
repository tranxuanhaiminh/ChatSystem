package database;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ressources.Databasequerries;

public class Databasecon {

	// Database location (in main folder)
	private String url = "jdbc:sqlite:";
	private String dbfile;
	private Connection c;
	
	public Databasecon(String dbfile) {
		this.dbfile=dbfile;
		
		try {
			this.c = DriverManager.getConnection(url + this.dbfile);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (createTable(c)) {
			System.out.println("The datbase is created !\n");
		}else {
			System.out.println("You already have a database !\n");
		}
	}
	


	/*
	 * Connect to the database from the databasefile name
	 */
	public Connection connect(String dbfile) {
		Connection c = null;
		try {
			c = DriverManager.getConnection(url + dbfile);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

	/*
	 * Check if table existed in database
	 */
	public boolean checktable(Connection conn, String table) {
		DatabaseMetaData md;
		try {
			md = conn.getMetaData();
			ResultSet rs = md.getTables(null, null, "chatHistory", null);
			return rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * Create table in database
	 */
	public boolean createTable(Connection c) {
		String sql = Databasequerries.createTable;

		try {
			Statement stmt = c.createStatement();
			stmt.execute(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * Get the sender name from the chat id
	 */
	public String getSender(Connection c, int id) {
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

	/*
	 * Get the receiver name from the chat id
	 */
	public String getReceiver(Connection c, int id) {
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

	/*
	 * Get the chat from the chat id
	 */
	public String getChatLine(Connection c, int id) {
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

	/*
	 * Get the chat sent time from the chat id
	 */
	public String getSentTime(Connection c, int id) {
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

	/*
	 * Insert chat to database
	 */
	public int insertChat(Connection c, String person, String chatline, String time, boolean sent) {
		String sql = null;
		if (sent) {
			sql = Databasequerries.insertChat1;
		} else {
			sql = Databasequerries.insertChat2;
		}
		try {
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, person);
			pstmt.setString(2, chatline);
			pstmt.setString(3, time);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	/*
	 * Get [limit] number of chat line in history with the most recent datetime starting from the [offset]th chat line
	 */
	public ResultSet getChatHistory(Connection c, String person, int limit, int offset) {
		String sql = Databasequerries.getChatHistory;
		try {
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, person);
			pstmt.setString(2, person);
			pstmt.setInt(3, limit);
			pstmt.setInt(4, offset);
			return pstmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	public Connection getC() {
		return c;
	}
}
