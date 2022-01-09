package chatsystem;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


public class MainMenu1{

	//Informations
	private ContactList contactList;
	private Contact me;
	// recevoir les contacts et les mettre dans la liste des contacts
	private ContactsManager cm=null;
	private JList<String> pseudosList = null;
	
	final private JFrame frame;
	final private JPanel userPanel;
	final private JPanel listPanel;
	final private JButton changepseudo;
	final private JLabel pseudoLabel;
	
	//	private JScrollPane scrollPane;
	final private JTable usertable=null;
	
	//modifying pseudo frame
	final private JFrame modifyFrame;
	final private JButton verifyPseudo;
	final private JTextField enterpseudo;
	final private JFrame startingChat;
	
	//Others size 
	private Dimension othersFrameSize;

	public MainMenu1(Contact m, ContactList l, ContactsManager cm) {
		
		// recevoir les contacts et les mettre dans la liste des contacts
		this.cm = cm;
		
		this.me = m; 
		this.contactList = l;
		
		othersFrameSize = new Dimension(200, 100);

		frame = new JFrame();
		Dimension frameSize = new Dimension(1000,700);
		frame.setSize(frameSize);
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
		
		frame.getContentPane().add(BorderLayout.NORTH, userPanel);
		//frame.getContentPane().add(BorderLayout.CENTER, listPanel);
		

		//System.out.print(usertable.getSize());
		
		////////////////////////////////////////////////////////////////////////////
		// Displaying the list of contacts
		
		DefaultListModel<String> pl = new DefaultListModel<String>();
		//adding the pseudos to the list
		for (Contact c : contactList.getList()) {
			pl.addElement(c.getPseudo());
		}
		pseudosList = new JList<String>(pl);
		pseudosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pseudosList.setLayoutOrientation(JList.VERTICAL);
		pseudosList.setVisibleRowCount(-1);
		
		//List listener 
		ListSelectionModel listSelectionModel = pseudosList.getSelectionModel();
	    listSelectionModel.addListSelectionListener(new Action(this));
		
		JScrollPane pseudosListScroller = new JScrollPane(pseudosList);
		pseudosListScroller.setPreferredSize(new Dimension(frameSize.width/5, frameSize.height*85/100));
		pseudosListScroller.setBorder(BorderFactory.createTitledBorder("Connected Users"));
		
		listPanel = new JPanel();
		listPanel.add(new JScrollPane(pseudosListScroller));
		
		frame.getContentPane().add(BorderLayout.EAST, listPanel);
		
		/////////////////////////////////////////////////////////////////////////////
		
		//modify pseudo 
		//Create and set up the window.
        this.modifyFrame = new JFrame("Modify your username");
        
        modifyFrame.setSize(othersFrameSize);
        
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
        
        // Starting a conversation
        this.startingChat = new JFrame("Starting A Chat Session");
        
        startingChat.setSize(othersFrameSize);
        
        //Create and set up the panel.
        JPanel startPanel = new JPanel(new GridLayout(2, 1));
        
        JLabel question = new JLabel("Do you want to start a Chat Session with this user ?");
        startingChat.add(question, BorderLayout.CENTER);
        
        JButton yesB = new JButton("Yes");
        yesB.addActionListener(this);
        JButton noB = new JButton("No");
        noB.addActionListener(new ActionListener)
        
        startPanel.add(yesB);
        
        startPanel.add(noB);
        
        startingChat.add(startPanel);
        
        //////////////////////////////////////////////////////////////////////////////
        frame.setVisible(true);

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
	
	
	public static void main(String[] args) {
		
		ContactList cl = new ContactList();
		
		cl.addContact(new Contact("titi","e"));
		cl.addContact(new Contact("tata","e"));
		cl.addContact(new Contact("tete","e"));
		cl.addContact(new Contact("tutu","e"));
		cl.addContact(new Contact("titi","e"));
		cl.addContact(new Contact("titi","e"));
		cl.addContact(new Contact("titi","e"));
		cl.addContact(new Contact("titi","e"));
		cl.addContact(new Contact("titi","e"));
		cl.addContact(new Contact("titi","e"));
		cl.addContact(new Contact("titi","e"));


		Contact me = new Contact("toto","127.0.0.1");
		ContactsManager cm=null;
		
		MainMenu1 mm= new MainMenu1(me, cl, cm);
		
	}



}
