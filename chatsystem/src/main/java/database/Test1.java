package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test1 {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("Helloworld\n");
		String url = "jdbc:sqlite:";
		String dbfile = "test.db";
		Connection conn = null;
		conn = DriverManager.getConnection(url + dbfile);
		DatabaseMetaData md = conn.getMetaData();
		ResultSet rs = md.getTables(null, null, "chatHistory", null);

		final File f = new File("test.db");

		if (rs.next()) {
			System.out.println("Database table exists!\n");
		} else {
			System.out.println("Database table does not exists!\n");
		}

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
	}

}
