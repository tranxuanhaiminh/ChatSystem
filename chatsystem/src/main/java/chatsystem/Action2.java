//package chatsystem;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
//import javax.swing.JTextField;
//import javax.swing.ListSelectionModel;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//
//import database.Databasecon;
//import network.IpAddress;
//import network.UDPSend;
//
//import javax.swing.Timer;
//
//import userinterface.Alert;
//import userinterface.ChatWindow;
//import userinterface.Connect;
//import userinterface.MainMenu;
//
//public class Action2 implements ActionListener, ListSelectionListener {
//
//	public Connect pageC;
//	public MainMenu pageM;
//	public ChatWindow pageW;
//	
//	/*
//	 * Fields
//	 */
//	private Databasecon dbcon; 
//
//	public Action2(Connect c) {
//		super();
//		this.pageC = c;
//		dbcon = new Databasecon();
//	}
//
//	public Action2(MainMenu m) {
//		super();
//		this.pageM = m;
//		dbcon = new Databasecon();
//	}
//
//	public Action2(ChatWindow c) {
//		super();
//		this.pageW = c;
//		dbcon = new Databasecon();
//	}
//
//	public void actionPerformed(ActionEvent event) {
//
//		if (pageC != null && (event.getSource().equals(pageC.getVerifyPseudo())
//				|| event.getSource().equals(pageC.getEnterPseudo()))) {
//			
//			System.out.println("Username submitted");
////			final Contact me = ContactList.getMe();
//
//			if (pageC.getEnterPseudo().getText() == null && !(pageC.getEnterPseudo().getText().equals(""))) {
//
//				new Alert("Please enter a value").setVisible(true);
//
//			} else {
//				ContactList.setMe(pageC.getEnterPseudo().getText());
////				me.setPseudo(pageC.getEnterPseudo().getText());
//
//				//We start the contact manager
////				pageC.getCm().setRunning(true);
//				
//				//We wait until the contacts have been added (3sec max)
//				synchronized (pageC.getContactList()) {
//					try {
//						pageC.getContactList().wait();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					new Alert("Error : Please close the program (connecting phase) ! ").setVisible(true);
//
//					}
//				}
//
////				pageC.getCm().setRunning(false);
//				final ContactList contactList = pageC.getContactList();
//
//				for (Contact i : contactList.getList())
//					System.out.println(i.getPseudo());
//
//				if (contactList.compareContact(ContactList.getMe()) == false) {
//
//					//Stopping the contacts manager
////					pageC.getCm().setRunning(false);
//
//					new Alert("This username is already used ! Try again").setVisible(true);
//
//					ContactList.getMe().setPseudo(null);
//
//				} else {
//
//					pageC.setMain(new MainMenu(contactList));
//					Alert welcome = new Alert("Welcome to the Chat System !");
//					welcome.setVisible(true);
//
//					Timer t = new Timer(700, new ActionListener() {
//						public void actionPerformed(ActionEvent e) {
//
//							welcome.setVisible(false);
//							pageC.setVisible(false);
//
//						}
//					});
//					t.setRepeats(false); // Only execute once
//					t.start();
//				}
//			}
//
//		} else if (pageM != null && event.getSource().equals(pageM.getChangepseudo())) { // modif pseudo
//
//			// Display the window.
////			pageM.getModifyFrame().setVisible(true);
//
////		} else if (pageM != null && (event.getSource().equals(pageM.getModifyFrame().getVerifyPseudo())
////				|| event.getSource().equals(pageM.getModifyFrame().getEnterpseudo()))) {
//
////			if (pageM.getModifyFrame().getEnterpseudo().getText() == null
////					|| pageM.getModifyFrame().getEnterpseudo().getText().equals("")) {
//
////				new Alert("Please enter a value").setVisible(true);
//
////			} else {
//
////				final String pseudo = pageM.getModifyFrame().getEnterpseudo().getText();
////				Contact p = new Contact(pseudo, (String) null);
//				ContactList contactList = pageM.getContactList();
//
////				if (contactList.comparePseudo(p) == false) {
//
//					new Alert("This username is already used !").setVisible(true);
//
////				} else {
//
//					Alert success = new Alert("Success !");
//					success.setVisible(true);
////					ContactList.getMe().setPseudo(pseudo);
//					pageM.getPseudoLabel().setText(ContactList.getMe().getPseudo());
////					pageM.getModifyFrame().getEnterpseudo().setText(null);
//
//					// Sending our username to everybody
////					ContactsManager cm = pageM.getCm();
////					cm.signalDatagram(ContactList.getMe().getPseudo(), "255.255.255.255");
//					UDPSend.send(ContactList.getMe(), IpAddress.getBroadcast());
//
//					Timer t = new Timer(500, new ActionListener() {
//						public void actionPerformed(ActionEvent e) {
//
//							success.setVisible(false);
////							pageM.getModifyFrame().setVisible(false);
//
//						}
//					});
//					t.setRepeats(false); // Only execute once
//					t.start();
////				}
////			}
//
//		} else if (pageW != null
//				&& (event.getSource().equals(pageW.getSendChat()) || event.getSource().equals(pageW.getChatInput()))) {
//
//			if (pageW.getConv() == null) {
//
//				new Alert("This user is not connected ! You can't send messages !").setVisible(true);
//
//			} else {
//				JTextField chatInput = pageW.getChatInput();
//
//				if (chatInput.getText() != null && !(chatInput.getText().equals(""))) {
//
//					// creating the message
//					Message msg = new Message(pageW.getDest(), chatInput.getText());
//
//					// Displaying the message on the chat window
//					pageW.addChatLine(msg, true);
//
//					//Sending the message to his destinator
//					pageW.getMain().getMessMan().sendMessTo(pageW.getConv(), msg);
//
//					// adding the message to the chat history
//					dbcon.insertChat(msg.getDest().getIpaddress().getHostAddress(), msg.getDest().getPseudo(), msg.toString(),
//							msg.convertDateToFormat(), true);
//					System.out.println("Adding the msg you re sending to "
//							+ msg.getDest().getIpaddress().getHostAddress() + " to the chat history\n");
//
//					chatInput.setText(null);
//				}
//			}
//
//		}
//	}
//
//	@Override
//	public void valueChanged(ListSelectionEvent e) {
//
//		ListSelectionModel lsm = (ListSelectionModel) e.getSource();
//
//		int indexr = pageM.getPseudosList().getSelectedRow();
//		int indexc = pageM.getPseudosList().getSelectedColumn();
//
//		if (indexr != -1 && indexc != -1) {
//			lsm.clearSelection();
//			if (indexc > 0) {
//				String pseudo = (String) pageM.getPseudosList().getValueAt(indexr, indexc);
//				Contact dest = pageM.getContactList().findP(pseudo);
//
//				if (dest != null) {
//
//					Conversation in = null;
//					in = pageM.getMessMan().getConv(dest);
//
//					if (in != null) {
//						System.out.println("You have already openned this conversation ! Check the open chat windows.\n");
//						in.getChatw().requestFocus();
//
//					} else if ((in = pageM.getMessMan().getStoppedConv(dest)) != null) {
//
//						System.out.println("You stopped this conversation. We are restarting it.\n");
//						new Conversation(pageM, dest);
//
//					} else {
//						System.out.println("Contact found. We are starting the conversation.\n");
//						new Conversation(pageM, dest);
//					}
//
//				} else {
//					System.out.println("This user is not connected, You can only look at the message history.\n");
//					new Alert("This user is not connected ! You can only look at the message history !").setVisible(true);
//					try {
//						new ChatWindow(pageM, new Contact(InetAddress.getByName(pseudo)), null);
//					} catch (UnknownHostException e1) {
//						e1.printStackTrace();
//						new Alert("Error : Please close the program (Main phase) !").setVisible(true);
//					}
//
//				}
//			}
//		}
//	}
//
//}