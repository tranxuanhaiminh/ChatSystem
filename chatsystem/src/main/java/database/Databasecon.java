package database;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Databasecon {

	// Database location (in main folder)
	private String url = "jdbc:sqlite:";
	private String dbfile;
	private static Connection c;
	
	public Databasecon(String dbfile) {
		this.dbfile=dbfile;
		
		try {
			this.c = DriverManager.getConnection(url + this.dbfile);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (createTable()) {
			System.out.println("The datbase is created !\n");
		}else {
			System.out.println("You already have a database !\n");
		}
	}

	public Databasecon() {
		
	}


//	/*
//	 * Connect to the database from the databasefile name
//	 */
//	public Connection connect(String dbfile) {
//		Connection c = null;
//		try {
//			c = DriverManager.getConnection(url + dbfile);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return c;
//	}

	/*
	 * Check if table existed in database
	 */
	public boolean checktable(String table) {
		DatabaseMetaData md;
		try {
			md = c.getMetaData();
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
	public boolean createTable() {
		String sql = "CREATE TABLE IF NOT EXISTS chatHistory (\n" + "	id integer NOT NULL PRIMARY KEY,\n"
				+ "	sender varchar NULL DEFAULT NULL,\n" + "	receiver varchar NULL DEFAULT NULL,\n"
				+ "	createdDate datetime NOT NULL,\n" + "	sentChat varchar NOT NULL\n" + ");";

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
	public String getSender(int id) {
		String sql = "SELECT sender FROM chatHistory WHERE id = ?";
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
	public String getReceiver(int id) {
		String sql = "SELECT receiver FROM chatHistory WHERE id = ?";
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
	public String getChatLine(int id) {
		String sql = "SELECT sentChat FROM chatHistory WHERE id = ?";
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
	public String getSentTime(int id) {
		String sql = "SELECT createdDate FROM chatHistory WHERE id = ?";
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
	public int insertChat(String person, String chatline, String time, boolean sent) {
		String sql = null;
		if (sent) {
			sql = "INSERT INTO chatHistory(receiver, sentChat, createdDate) VALUES(?, ?, ?)";
		} else {
			sql = "INSERT INTO chatHistory(sender, sentChat, createdDate) VALUES(?, ?, ?)";
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
	public ResultSet getChatHistory(String person, int limit, int offset) {
		String sql = "SELECT sender, receiver, sentChat "
				+ "FROM chatHistory "
				+ "WHERE sender = ? OR receiver = ? "
				+ "ORDER BY datetime(createdDate) DESC Limit ? OFFSET ?";
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
