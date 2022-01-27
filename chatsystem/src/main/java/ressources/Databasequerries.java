package ressources;

public class Databasequerries {

	/* Properties */
	public static String createTable = "CREATE TABLE IF NOT EXISTS chatHistory\n"
			+ "(id int NOT NULL PRIMARY KEY,\n" + "ip varchar NOT NULL,\n" + "pseudo varchar NOT NULL,\n"
			+ "sentChat varchar NOT NULL,\n" + "sent integer NOT NULL,\n" + "createdDate datetime NOT NULL);";

	public static String getSentTime = "SELECT createdDate FROM chatHistory WHERE id = ?";

	public static String getContacts = "SELECT ip, pseudo FROM chatHistory ORDER BY datetime(createdDate) DESC";

	public static String insertChat1 = "INSERT INTO chatHistory(ip, pseudo, sentChat, sent, createdDate) VALUES(?, ?, ?, ?, ?)";

	public static String getChatHistory = "SELECT ip, sentChat, sent " + "FROM chatHistory " + "WHERE ip = ?"
			+ "ORDER BY datetime(createdDate) DESC Limit ? OFFSET ?";
}