package chatsystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import networkconnection.BroadcastReceiver;

public class MainMenu1{

	//Informations
	private ContactList contactList;
	private Contact me;
	
	//private Action modifypseudo;
	
	//private static String pseudo = "toto";
	
	final private JFrame frame;
	final private JPanel userPanel;
	final private JPanel listPanel;
	final private JButton changepseudo;
	final private JLabel pseudoLabel;
	
	//	private JScrollPane scrollPane;
	final private JTable usertable;
	
	//modifying pseudo frame
	final private JFrame modifyFrame;
	final private JButton verifyPseudo;
	final private JTextField enterpseudo;
	
	// recevoir les contacts et les mettre dans la liste des contacts
	private ContactsManager cm;

	public MainMenu1(Contact m, ContactList l) {
		
		// recevoir les contacts et les mettre dans la liste des contacts
		this.cm = new ContactsManager(this);
		cm.start();
		
		this.me = m; 
		this.contactList = l;

		String[] columnNames = {"Pseudo"};

		String userlist[][] = {
				{"titi"},
				{"tata"},
				{"tutu"}
		};


		frame = new JFrame();
		frame.setSize(1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		userPanel = new JPanel();
		userPanel.setBackground(Color.gray);

		pseudoLabel = new JLabel(me.getPseudo());
		pseudoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 30));

		this.changepseudo = new JButton("Modify");
		changepseudo.addActionListener(new Action(this));

		userPanel.add(pseudoLabel);
		userPanel.add(getChangepseudo());

		usertable = new JTable(userlist, columnNames);
		usertable.setTableHeader(null);

		listPanel = new JPanel();
		listPanel.add(new JScrollPane(usertable));

		frame.getContentPane().add(BorderLayout.NORTH, userPanel);
		frame.getContentPane().add(BorderLayout.CENTER, listPanel);
		frame.setVisible(true);

		System.out.print(listPanel.getSize());
		System.out.print(usertable.getSize());
		
		/////////////////////////////////////////////////////////////////////////////
		
		//modify pseudo 
		//Create and set up the window.
        this.modifyFrame = new JFrame("Modify your username");
        //connectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        modifyFrame.setSize(new Dimension(200, 100));
        
        //Create and set up the panel.
        JPanel modifyPanel = new JPanel(new GridLayout(2, 1));
        
        //widgets
        enterpseudo = new JTextField();
        enterpseudo.setSize(new Dimension(10, 100));
        
        
        this.verifyPseudo = new JButton("Modify");
        getVerifyPseudo().addActionListener(new Action(this));
        
        modifyPanel.add(enterpseudo);
        modifyPanel.add(getVerifyPseudo());
        
        //Add the panel to the window.
        modifyFrame.getContentPane().add(modifyPanel, BorderLayout.CENTER);
        
        /////////////////////////////////////////////////////////////////////////////

	}


	public Contact getMe() {
		return me;
	}
	
	
	
	public ContactList getContactList() {
		return contactList;
	}

	public JButton getChangepseudo() {
		return changepseudo;
	}

	public JFrame getModifyFrame() {
		return modifyFrame;
	}

	public JButton getVerifyPseudo() {
		return verifyPseudo;
	}

	public JTextField getEnterpseudo() {
		return enterpseudo;
	}
	
	public JLabel getPseudoLabel() {
		return pseudoLabel;
	}
	
	public ContactsManager getCm() {
		return cm;
	}


}
