package chatsystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
	
	//lien page principale
	private MainMenu1 mm;
	
	//listeners
	private Action sendMess;
	
	public ChatWindow1(MainMenu1 m,Contact dest) {
		this.mm = m;
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
		chatInput.addActionListener(this.sendMess);

		
		sendChat = new JButton("Send");
		sendChat.addActionListener(this.sendMess);
		
		
		chatPanel.add(chatInput);
		chatPanel.add(sendChat);
		
		chatHistory = new JPanel();
		chatHistory.setBorder(BorderFactory.createTitledBorder("Chat History"));
		
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
		new ChatWindow1(null,p);

	}
	
	public void addMesg(Message m) { 
		JLabel jl= new JLabel(m.toString());
		chatHistory.add(jl,BorderLayout.AFTER_LAST_LINE);
		frame.setVisible(true);
	}


	public MainMenu1 getMm() {
		return mm;
	}

}
