package chatsystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

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
	private Contact dest;
	
	//
	private Action sendMess;
	
	public ChatWindow(Contact dest) {
		
		this.dest = dest;
		
		this.sendMess = new Action(this);
		
		frame = new JFrame(this.dest.getPseudo());
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550, 400);
		
		chatPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		chatInput = new JTextField(40);
		chatInput.setMaximumSize(new Dimension(1000, 30));
		chatInput.setMinimumSize(new Dimension(300, 30));
		chatInput.setLayout(new BorderLayout(30, 30));
		
		sendChat = new JButton("Send");
		sendChat.addActionListener(this.sendMess);
		
		chatPanel.add(chatInput);
		chatPanel.add(sendChat);
		
		chatHistory = new JPanel();
		
		frame.getContentPane().add(BorderLayout.SOUTH, chatPanel);
		frame.getContentPane().add(BorderLayout.CENTER, chatHistory);
		frame.setVisible(true);
		
		System.out.print(chatInput.getSize());
		
	}

	
	public Action getSendMess() {
		return this.sendMess;
	}
	
	
	public static void main(String[] args) {
		Contact p = new Contact("titi");
		new ChatWindow(p);

	}

}
