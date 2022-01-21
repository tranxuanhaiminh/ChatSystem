package ressources;

public class Databasequerries {

	/* Properties */
	public static String createTable = "CREATE TABLE IF NOT EXISTS chatHistory (\n"
			+ "	id integer NOT NULL PRIMARY KEY,\n" + "	sender varchar NULL DEFAULT NULL,\n"
			+ "	receiver varchar NULL DEFAULT NULL,\n" + "	senderpseudo varchar NULL DEFAULT NULL,\n"
			+ "	receiverpseudo varchar NULL DEFAULT NULL,\n" + "	createdDate datetime NOT NULL,\n"
			+ "	sentChat varchar " + "NOT NULL\n" + ");";

	public static String getSender = "SELECT sender FROM chatHistory WHERE id = ?";

	public static String getReceiver = "SELECT receiver FROM chatHistory WHERE id = ?";

	public static String getChatLine = "SELECT sentChat FROM chatHistory WHERE id = ?";

	public static String getSentTime = "SELECT createdDate FROM chatHistory WHERE id = ?";

	public static String getContacts = "SELECT sender, senderpseudo, receiver, receiverpseudo FROM chatHistory";

	public static String insertChat1 = "INSERT INTO chatHistory(sender, senderpseudo, sentChat, createdDate) VALUES(?, ?, ?)";

	public static String insertChat2 = "INSERT INTO chatHistory(receiver, receiverpseudo, sentChat, createdDate) VALUES(?, ?, ?)";

	public static String getChatHistory = "SELECT sender, receiver, sentChat " + "FROM chatHistory "
			+ "WHERE sender = ? OR receiver = ? " + "ORDER BY datetime(createdDate) DESC Limit ? OFFSET ?";
}