package chatsystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.Databasecon;
import service.ButtonService;
import userinterface.Alert;
import userinterface.ChatWindow;
import userinterface.Connect;
import userinterface.MainMenu;

public class Action implements ActionListener, ListSelectionListener {
	
	/*
	 * Fields
	 */
	private Databasecon dbcon; 

	/**
	 * Perform action based on which event occured
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub	
		
		// Get the source frame
		Component src = (Component) e.getSource();
        JFrame frame = (JFrame) SwingUtilities.getRoot(src);
		
		// If the source frame is Connect then register new username
		if (frame instanceof Connect) {
			ButtonService.submitPseudo((Connect) frame);
		}
		
		// If the source frame is MainMenu then modify current username
		else if (frame instanceof MainMenu) {
			ButtonService.modifyPseudo((MainMenu) frame);
		}
		
		// If the source frame is ChatWindow then send the chat line
		else if (frame instanceof ChatWindow) {
			ButtonService.sendChat((ChatWindow) frame);
		}
		
	}

	/**
	 * Start conversation with selected user on the table
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
		ListSelectionModel lsm = (ListSelectionModel) e.getSource();
		
		System.out.println(lsm);
		
		if (!lsm.isSelectionEmpty()) {
			// Find out which indexes are selected.
            int minIndex = lsm.getMinSelectionIndex();
            int maxIndex = lsm.getMaxSelectionIndex();
            for (int i = minIndex; i <= maxIndex; i++) {
                if (lsm.isSelectedIndex(i)) {
                	// Get the corresponding contact
                    Contact dest = ContactList.findP(MainMenu.getPseudo(i));
                    if (dest != null) {
                    	
						Conversation in = null;
						in = MessagesManager.getConv(dest);

						if (in != null) {
							in.getChatw().requestFocus();

						} else if ((in = MessagesManager.getStoppedConv(dest)) != null) {
							new Conversation(dest);

						} else {
							new Conversation(dest);
						}

					} else {
						new Alert("This user is not connected ! You can only look at the message history !")
								.setVisible(true);
						try {
							new ChatWindow(new Contact(InetAddress.getByName(dest.getPseudo())), null);
						} catch (UnknownHostException e1) {
							e1.printStackTrace();
							new Alert("Error : Please close the program (Main phase) !").setVisible(true);
						}

					}
                	break;
                }
            }
        }

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
		
	}
}