package chatsystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.Databasecon;
import service.ButtonService;
import userinterface.ChatWindow;
import userinterface.Connect;
import userinterface.MainMenu;

public class Action implements ActionListener, ListSelectionListener {

	public Connect pageC;
	public MainMenu pageM;
	public ChatWindow pageW;
	
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

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}