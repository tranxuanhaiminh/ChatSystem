package chatsystem;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatWindow {
	
	private JFrame frame;
	private JPanel chatPanel;
	private JTextField chatInput;
	private JButton sendChat;
	private JPanel chatHistory;
	private String pseudo;
	
	public ChatWindow(String pseudo) {
		
		this.pseudo = pseudo;
		frame = new JFrame(this.pseudo);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550, 400);
		
		chatPanel = new JPanel();
		
		chatInput = new JTextField(30);
		
		sendChat = new JButton("Send");
		
		chatPanel.add(chatInput);
		chatPanel.add(sendChat);
		
		chatHistory = new JPanel();
		
		frame.getContentPane().add(BorderLayout.SOUTH, chatPanel);
		frame.getContentPane().add(BorderLayout.CENTER, chatHistory);
		frame.setVisible(true);
		
	}

	public static void main(String[] args) {

		new ChatWindow("titi");

	}

}
