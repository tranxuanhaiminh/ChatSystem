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
	
	private Action modifypseudo;
	
	//private static String pseudo = "toto";
	
	private JFrame frame;
	private JPanel userPanel;
	private JPanel listPanel;
	private JButton changepseudo;
	private JLabel pseudoLabel;
	//	private JScrollPane scrollPane;
	private JTable usertable;

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

		changepseudo = new JButton("Modify");
		//changepseudo.addActionListener(modifypseudo);

		userPanel.add(pseudoLabel);
		userPanel.add(changepseudo);

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

	}

		/*public static void main(String[] args) {
			new MainMenu();
		}*/

}
