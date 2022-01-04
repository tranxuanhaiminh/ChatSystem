package chatsystem;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import networkconnection.BroadcastReceiver;

public class Connection {
	
	private ContactList contactList;
	private Contact me;
	JTextField enterpseudo;
	private JFrame connectionFrame;
	private Action verify;
	private JButton verifyPseudo;

	// recevoir les contacts et les mettre dans la liste des contacts
	private ContactsManager cm;
	
	//page principale
	private MainMenu1 main;
	
	
	public Connection() {
		
		contactList = new ContactList();
		
		Contact c = new Contact("toto","120.1.1.0");
		contactList.addContact(c);
		
		me =new Contact();
		
		// iNitailisation du contacts manager
		cm = null;
		
		this.verify = new Action(this);
		
        //Create and set up the window.
        connectionFrame = new JFrame("Connection to the Chat System");
        //connectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        connectionFrame.setSize(new Dimension(200, 100));
        
        //Create and set up the panel.
        JPanel connectionPanel = new JPanel(new GridLayout(2, 1));
        
        //widgets
        enterpseudo = new JTextField();
        enterpseudo.setSize(new Dimension(10, 100));
        
        
        verifyPseudo = new JButton("Connect");
        verifyPseudo.addActionListener(verify);
        
        connectionPanel.add(enterpseudo);
        connectionPanel.add(verifyPseudo);
        
        //Add the panel to the window.
        connectionFrame.getContentPane().add(connectionPanel, BorderLayout.CENTER);
        
        //Display the window.
        connectionFrame.setLocationRelativeTo(null); // au centre
        connectionFrame.setVisible(true);
        
	}
	



	private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        Connection connection = new Connection();
    }
	
	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }



	public Contact getMe() {
		return me;
	}
	

	public ContactList getContactList() {
		return contactList;
	}
	
	public JTextField geText() {
		return enterpseudo;
	}

	public JFrame getConnectionFrame() {
		return connectionFrame;
	}

	public JButton getVerifyPseudo() {
		return verifyPseudo;
	}

	public ContactsManager getCm() {
		return cm;
	}
	
	public void setCm(ContactsManager m) {
		cm = m;
	}

	public MainMenu1 getMain() {
		return main;
	}


	public void setMain(MainMenu1 main) {
		this.main = main;
	}

}
