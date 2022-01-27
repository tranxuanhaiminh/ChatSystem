/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package userinterface;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import database.Databasecon;
import entities.Contact;
import entities.Conversation;
import entities.ConversationList;
import entities.Message;
import network.UDPSend;
import ressources.Interfacedisplay;
import service.Action;

/**
 *
 * @author Minh
 */
public class ChatWindow extends javax.swing.JFrame {

	/**
	 * Fields
	 */
	private static final long serialVersionUID = 1L;
	private final static String newline = "\n";
	private JScrollBar bar;
	private Contact contact;
	private Conversation conversation;

	// listeners
	private Action sendMess;

	// Number of messages to load from the database
	private final int nbMsgToLoad = 20;

	/**
	 * Creates new form NewJFrame
	 */
	public ChatWindow(Contact c) {
		
		contact = c;
		
		initComponents();
		
		// Remove conversation and send HC flag change when closing this chat window
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				if ((conversation != null) && conversation.getHalfClose()) {					
					ConversationList.removeConv(conversation);
				} else {
					UDPSend.send("HC", contact.getIpaddress());
				}
				dispose();
			}
		});

		// Loading the history
		loadHistory(contact, nbMsgToLoad, 0);

		setVisible(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
//	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		setTitle(contact.getPseudo());

		msg_input = new javax.swing.JTextField();
		msg_send = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		msg_display = new javax.swing.JTextArea();

		msg_send.setText(Interfacedisplay.sendbutton);
		msg_send.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				msg_sendActionPerformed(evt);
			}
		});

		jScrollPane1.setHorizontalScrollBar(null);

		msg_display.setEditable(false);
		msg_display.setColumns(20);
		msg_display.setLineWrap(true);
		msg_display.setRows(5);
		msg_display.setText("");
		jScrollPane1.setViewportView(msg_display);
		
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1)
						.addGroup(layout.createSequentialGroup()
								.addComponent(msg_input, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(msg_send, javax.swing.GroupLayout.PREFERRED_SIZE, 73,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
						.addGap(8, 8, 8)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(msg_send, javax.swing.GroupLayout.PREFERRED_SIZE, 31,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(msg_input, javax.swing.GroupLayout.PREFERRED_SIZE, 31,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		this.sendMess = new Action();
		msg_input.addActionListener(sendMess);
		msg_send.addActionListener(sendMess);
		
		
		
		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void msg_sendActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_msg_sendActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_msg_sendActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea msg_display;
	private javax.swing.JTextField msg_input;
	private javax.swing.JButton msg_send;
	// End of variables declaration//GEN-END:variables

	/**
	 * Add chatline to the chat window and insert to the database
	 * 
	 * @param chatline
	 * @param isMe
	 */
	public void addChatLine(Message chatline, boolean isMe) {
		if (isMe) {
			msg_display.append("Me : " + chatline.toString() + newline);
		} else {
			msg_display.append(contact.getPseudo() + " : " + chatline.toString() + newline);
		}
		bar = jScrollPane1.getVerticalScrollBar();
		bar.setValue(bar.getMaximum());
		this.requestFocus();
	}

	/**
	 * Load chat history and add to the chatwindow
	 * 
	 * @param limit
	 * @param offset
	 */
	public void loadHistory(Contact contact, int limit, int offset) {

		System.out.println("Loading the chat history\n");

		ResultSet rs = Databasecon.getChatHistory(contact.getIpaddress().getHostAddress(), limit, offset);
		System.out.println(rs);
		try {
			while (rs.next()) {
				String chatline = rs.getString("sentChat");
				String person = (rs.getInt("sent") == 1) ? "Me" : contact.getPseudo();
				try {
					msg_display.getDocument().insertString(0, person + " : " + chatline + newline, null);
					msg_display.setCaretPosition(0);
				} catch (BadLocationException e) {
					e.printStackTrace();
					new Alert("Error : Please close the program!\n");

				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
			new Alert("Error : Please close the program!\n");
		}
		bar = jScrollPane1.getVerticalScrollBar();
		bar.setValue(bar.getMaximum());
	}

	/* Getters and setters */
	public JTextField getChatInput() {
		return msg_input;
	}

	public Contact getContact() {
		return contact;
	}

	public Conversation getConv() {
		return conversation;
	}
	
	public void setConv(Conversation conv) {
		conversation = conv;
	}
}