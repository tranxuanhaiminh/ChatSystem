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

public class MainMenu {

	private static String pseudo = "toto";
	private JFrame frame;
	private JPanel userPanel;
	private JPanel listPanel;
	private JButton changepseudo;
	private JLabel pseudoLabel;
	//	private JScrollPane scrollPane;
	private JTable usertable;

	public MainMenu() {

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

		pseudoLabel = new JLabel(pseudo);
		pseudoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 30));

		changepseudo = new JButton("Modify");

		userPanel.add(pseudoLabel);
		userPanel.add(changepseudo);

		usertable = new JTable(userlist, columnNames);
		usertable.setTableHeader(null);

		listPanel = new JPanel();
		listPanel.add(new JScrollPane(usertable));

		frame.getContentPane().add(BorderLayout.NORTH, userPanel);
		frame.getContentPane().add(BorderLayout.CENTER, listPanel);
		frame.setVisible(true);

		System.out.print(listPanel.getSize());
		System.out.print(usertable.getSize());

	}

		public static void main(String[] args) {
			new MainMenu();
		}

}
