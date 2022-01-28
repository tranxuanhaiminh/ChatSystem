package service;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import database.Databasecon;
import entities.Contact;
import entities.ContactList;
import entities.Conversation;
import entities.ConversationList;
import entities.Message;
import network.IpAddress;
import network.TCPListener;
import network.UDPSend;
import ressources.AlertMessage;
import ressources.Interfacedisplay;
import userinterface.Alert;
import userinterface.ChatWindow;
import userinterface.Connect;
import userinterface.MainMenu;

public class ButtonService {

	/**
	 * Verify the pseudo entered then connect the user
	 * 
	 * @param frame
	 */
	public static void submitPseudo(Connect frame) {

		// Get the username from the frame
		String pseudo = Connect.getEnterPseudo().getText();

		// If the username field is empty
		if (pseudo.equals("")) {
			new Alert(AlertMessage.emptyUsername);
		}
		// If the username is duplicated
		else if (ContactList.isDupPseudo(pseudo)) {
			new Alert(AlertMessage.dupUsername);
		}
		// If the username is valid
		else {
			// Register new username
			ContactList.setMe(pseudo);

			// If the event occured in the connecting phase
			if (Connect.getbuttonSubmit().getText().equals(Interfacedisplay.connectbutton)) {
				new MainMenu();
				new TCPListener();
			}
			// If the event occured in the main phase
			else {
				MainMenu.setUsernameLabel(pseudo);
			}
			frame.dispose();

			// Start timer for verification
			UDPService.setTimer();

			// Broadcast for confirmation
			UDPSend.send(ContactList.getMe(), IpAddress.getBroadcast());
		}
	}

	/**
	 * Open the modify username frame
	 * 
	 * @param frame
	 */
	public static void modifyPseudo(MainMenu frame) {
		new Connect(Interfacedisplay.modifybutton);
	}

	/**
	 * Send the chat line on the specified frame to the corresponding user
	 * 
	 * @param frame
	 */
	public static void sendChat(ChatWindow frame) {

		// Get chat line
		String chatline = frame.getChatInput().getText();

		// If the user is not connected
		if (ConversationList.findConv(frame.getContact().getIpaddress()) == null) {
			new Alert(AlertMessage.offlineUser);
		}

		// If the chat is not empty
		else if (!chatline.equals("")) {

			// Create message
			Message msg = new Message(frame.getConv().getDest(), chatline);

			// Send message
			frame.getConv().sendChat(msg);

			// Add to chat window
			frame.addChatLine(msg, true);

			// Clear chat field
			frame.getChatInput().setText("");

			// Add to database
			Databasecon.insertChat(msg.getIp(), msg.getPseudo(), msg.toString(), msg.getDate(), true);
		}
	}

	/**
	 * Open chat with clicked user. Start conversation if not existed
	 * 
	 * @param i
	 */
	public static void startChat(int i) {

		// Remove bold font from the main menu
		MainMenu.undoMessageNoti(i);

		// Get the pseudo clicked
		String pseudo = MainMenu.getPseudo(i);

		// Get the corresponding contact
		Contact contact;

		// If contact exists in the list
		if ((contact  = ContactList.findContact(pseudo)) != null) {

			// Get the conversation
			Conversation conv = ConversationList.findConv(pseudo);

			// If a TCP connection with the contact existed
			if (conv != null) {
				// Show chat window
				if ((conv.getChatw() != null) && (conv.getChatw().isVisible())) {
					conv.getChatw().requestFocus();
				} else {
					conv.startChat();
					;
				}
			}

			// If no TCP connection with the contact exists
			else {
				try {
					// establish a tcp connection and show chat window
					Conversation conversation = new Conversation(new Socket(contact.getIpaddress(), 55555));
					conversation.startChat();
				} catch (ConnectException e1) {
					// The other user's program ended unexpectedly
					ContactList.removeContact(contact);
					try {
						MainMenu.modUser(contact.getPseudo(), contact.getPseudo(), false);
					} catch (NullPointerException e) {}
					new Alert(AlertMessage.disconnectedUser);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					new Alert(AlertMessage.error);
				}
			}

		}
		// User not connected, read chat history only
		else if ((contact = ContactList.findOffline(pseudo)) != null) {
			ConversationList.addWindow(new ChatWindow(contact));
		}
	}
}
