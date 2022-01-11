package database;

import java.sql.*;

public class Testcon {
	
  public static void main( String args[] ) {
	  
	  // Database location (in main folder)
	  String url = "jdbc:sqlite:";
	  
	  // Database file name
	  String dbfile = "test.db";
      Connection c = null;
      
      // Ceate table query
      String sql = "CREATE TABLE IF NOT EXISTS warehouses (\n"
              + "	id integer PRIMARY KEY,\n"
              + "	name text NOT NULL,\n"
              + "	capacity real\n"
              + ");";
      
      // Add data query
      String sql1 = "INSERT INTO warehouses(name, capacity) VALUES(?, ?)";
      
      // Get data query
      String sql2 = "SELECT id, name, capacity FROM warehouses";
      
      // Update data query
      String sql3 = "UPDATE warehouses SET name = ? , "
              + "capacity = ? "
              + "WHERE id = ?";

      // Delete data query
      String sql4 = "DELETE FROM warehouses WHERE id = ?";
      
      // Delete table
      String sql5 = "DROP TABLE warehouses";

      try {
         //Class.forName("org.sqlite.JDBC");
    	  
    	  // Connect to database, create new if not existed
         c = DriverManager.getConnection(url + dbfile);
         
         // Create statement
//         Statement stmt = c.createStatement();
         PreparedStatement pstmt = c.prepareStatement(sql1);
//         PreparedStatement pstmt1 = c.prepareStatement(sql3);
//         PreparedStatement pstmt2 = c.prepareStatement(sql4);
//         Statement stmt2 = c.createStatement();
         
         // Execute query on created statement to create table
//         stmt.execute(sql);
         
         // Set parameters to avoid SQL injection
//         pstmt.setString(1, "Raw Materials");
//         pstmt.setDouble(2, 3000);

         // Add data (no need to pass sql, already did since statement creation)
         pstmt.executeUpdate();
         
         // Create variable to store result
//         ResultSet rs = stmt.executeQuery(sql2);
         
         // Get value from result set
//         while (rs.next()) {
//             System.out.println(rs.getInt("id") +  "\t" + 
//                                rs.getString("name") + "\t" +
//                                rs.getDouble("capacity"));
//         }
         
         // Update table
//         pstmt1.setString(1, "Finished Products");
//         pstmt1.setDouble(2, 5500);
//         pstmt1.setDouble(3, 3);
//         pstmt1.executeUpdate();
         
         // Delete data from table
//         pstmt2.setInt(1, 3);
//         pstmt2.executeUpdate();
         
         // Delete table
//         stmt2.execute(sql5);
         
         // Transaction
//         c.setAutoCommit(false);
//         c.commit();
         
         // If smt goes wrong(rowAffected != 1 / c != null / Exception e)
//         c.rollback();

      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      System.out.println("Opened database successfully");
   }
}