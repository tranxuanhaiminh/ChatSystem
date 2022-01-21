package service;

import chatsystem.ContactList;
import chatsystem.Conversation;
import chatsystem.Message;
import chatsystem.MessagesManager;
import database.Databasecon;
import network.IpAddress;
import network.UDPSend;
import ressources.Interfacedisplay;
import userinterface.Alert;
import userinterface.ChatWindow;
import userinterface.Connect;
import userinterface.MainMenu;

public class ButtonService {

	/* Methods */

	/**
	 * Verify the pseudo entered and
	 * 
	 * @param frame
	 */
	public static void submitPseudo(Connect frame) {

		// Get the username from the frame
		String pseudo = frame.getEnterPseudo().getText();
		System.out.println(pseudo);

		// If the username field is empty
		if (pseudo.equals("")) {
			new Alert("Please choose an username");
		}
		// If the username is duplicated
		else if (ContactList.isDupPseudo(pseudo)) {
			new Alert("Duplicate username");
			System.out.println("username duplicated");
		}
		// If the username is valid
		else {
			// Register new username
			ContactList.setMe(pseudo);

			// Broadcast for confirmation
			UDPSend.send(ContactList.getMe(), IpAddress.getBroadcast());

			// If the event occured in the connecting phase
			if (frame.getbuttonSubmit().getText().equals(Interfacedisplay.connectbutton)) {
				new MainMenu();
			}
			// If the event occured in the main phase
			else {
				MainMenu.setUsernameLabel(pseudo);
			}
			frame.dispose();

			// Start timer for verification
			UDPService.setTimer();
		}
	}

	public static void modifyPseudo(MainMenu frame) {
		new Connect(Interfacedisplay.modifybutton);
	}

	public static void sendChat(ChatWindow frame) {

		// Get chat line
		String chatline = frame.getChatInput().getText();

		// Get conversation
		Conversation conv = frame.getConv();

		// If the user has disconnected
		if (conv == null) {
			new Alert("This user is not connected ! You can't send messages !");
		}

		// If the chat is not empty
		else if (!chatline.equals("")) {

			// Create message
			Message msg = new Message(frame.getDest(), chatline);

			// Send message
			MessagesManager.sendMessTo(conv, msg);

			// Add to chat window
			frame.addChatLine(msg, true);

			// Clear chat field
			frame.getChatInput().setText("");

			// Add to database
			Databasecon.insertChat(msg.getIp(), msg.getPseudo(), msg.getMsg(), msg.getDate(), true);
		}
	}
}
