package service;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import chatsystem.ChatSystem;
import entities.Contact;
import entities.ContactList;
import entities.Conversation;
import entities.ConversationList;
import userinterface.Alert;
import userinterface.ChatWindow;
import userinterface.Connect;
import userinterface.MainMenu;

public class Action implements ActionListener, ListSelectionListener {

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
		
		if (!lsm.isSelectionEmpty()) {
			// Find out which indexes are selected.
            int minIndex = lsm.getMinSelectionIndex();
            int maxIndex = lsm.getMaxSelectionIndex();
            for (int i = minIndex; i <= maxIndex; i++) {
                if (lsm.isSelectedIndex(i)) {
                	// Clear selection
                	lsm.clearSelection();
                	
                	// Handle contact click
                	final int index = i;
					ChatSystem.threadpool.submit(() -> ButtonService.startChat(index));
                	break;
                }
            }
        }
	}
}