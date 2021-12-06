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

public class MainMenu{

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

	public MainMenu(Contact m, ContactList l) {
		
		this.me = m; 
		this.contactList = l;
		
		//this.modifypseudo = new Action

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

		userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		userPanel.setBackground(Color.gray);
		//		userPanel.setLayout(null);
		//		userPanel.setPreferredSize(new Dimension(500, 50));
		//		listPanel.setSize(690, 300);

		pseudoLabel = new JLabel(me.getPseudo());
		pseudoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 30));

		this.changepseudo = new JButton("Modify");
		changepseudo.addActionListener(new Action(this));

		userPanel.add(pseudoLabel);
		userPanel.add(getChangepseudo());

		usertable = new JTable(userlist, columnNames);
		//		usertable.setBounds(10, 50, 200, 300);
		usertable.setTableHeader(null);

		//		scrollPane = new JScrollPane(usertable);
		//		scrollPane.setPreferredSize(new Dimension(950, 300));
		//		usertable.setFillsViewportHeight(true);

		listPanel = new JPanel();
		listPanel.add(new JScrollPane(usertable));

		frame.getContentPane().add(BorderLayout.NORTH, userPanel);
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

		/*public static void main(String[] args) {
			new MainMenu();
		}*/
	

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


}
