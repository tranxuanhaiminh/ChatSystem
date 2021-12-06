package chatsystem;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Connection implements ActionListener{
	
	private ContactList contactList;
	private Contact me;
	JTextField enterpseudo;
	JFrame connectionFrame;
	
	public Connection() {
		
		contactList = new ContactList();
		Contact c = new Contact("toto","120.1.1.0");
		contactList.addContact(c);
		me =new Contact();
		
        //Create and set up the window.
        connectionFrame = new JFrame("Connection to the Chat System");
        //connectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        connectionFrame.setSize(new Dimension(200, 100));
        
        //Create and set up the panel.
        JPanel connectionPanel = new JPanel(new GridLayout(2, 1));
        
        //widgets
        enterpseudo = new JTextField();
        enterpseudo.setSize(new Dimension(10, 100));
        
        
        JButton verifyPseudo = new JButton("Connect");
        verifyPseudo.addActionListener(this); //new ActionButtonConnect());
        
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




	public void actionPerformed(ActionEvent event) {
		
		final JFrame okFrame = new JFrame("Connecting....");
		
		me.setPseudo(enterpseudo.getText());

		if (contactList.comparePseudo(me)==false) {
				okFrame.add(new JLabel("Your pseudo is already used ! Please enter a new one !"));
				
				//Display the window.
				okFrame.setSize(500, 100);
		        okFrame.setLocationRelativeTo(null);
		        okFrame.setVisible(true);
		        me.setPseudo(null);
		        
		} else {

			okFrame.add(new JLabel("Welcome to the ChatSystem !"));
			//Display the window.
			okFrame.setSize(250, 100);
	        okFrame.setLocationRelativeTo(null);
	        okFrame.setVisible(true);
	        
	        Timer t = new Timer(500, new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	okFrame.setVisible(false);
	            	okFrame.dispose();
	            	connectionFrame.setVisible(false);
	            	connectionFrame.dispose();
	    			MainMenu Main = new MainMenu(me, contactList);
	            }
	        });
	        t.setRepeats(false); // Only execute once
	        t.start();
		}
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


}
