package chatsystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatWindow1 {
	
	private JFrame frame;
	private JPanel chatPanel;
	private JTextField chatInput;
	private JButton sendChat;
	private JPanel chatHistory;
	private Contact dest;
	
	//listeners
	private Action sendMess;
	
	public ChatWindow1(Contact dest) {
		
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
		
		//System.out.print(chatInput.getSize());
		
	}

	
	public Action getSendMess() {
		return this.sendMess;
	}
	
	public JTextField getChatInput() {
		return chatInput;
	}
	
	public Contact getDest() {
		return dest;
	}
	public JButton getSendChat() {
		return sendChat;
	}
	
	public static void main(String[] args) {
		Contact p = new Contact("titi");
		new ChatWindow1(p);

	}
	
	public void addMesg(Message m) { //à faire
		JLabel jl= new JLabel(m.toString());
		frame.getContentPane().add(jl);
	}

}
