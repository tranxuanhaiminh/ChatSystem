package chatsystem;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import networkconnection.BroadcastReceiver;
import networkconnection.BroadcastSender;

public class Action implements ActionListener{
	
	public Connection pageC;
	public MainMenu1 pageM;
	public ChatWindow1 pageW;
	
	public Action(Connection c){
		super();
		this.pageC = c;
	}
	
	public Action(MainMenu1 m)  {
		super();
		this.pageM = m;
	}
	
	public Action(ChatWindow1 c) {
		super();
		this.pageW = c;
	}

	public void actionPerformed(ActionEvent event) {
		
		// pour la premiere connection
		if(pageC != null && event.getSource().equals(pageC.getVerifyPseudo())) {
			
			final JFrame okFrame = new JFrame("Connecting....");
			
			final Contact me = pageC.getMe();
        	me.setPseudo(pageC.geText().getText());			
			final JFrame connectionFrame = pageC.getConnectionFrame();

			//on crée et on lance le contact manager
			pageC.setCm(new ContactsManager(pageC));
			pageC.getCm().start();
			
			try {
				//on attend de finir de recevoir les contacts
				Thread.sleep(3300);
			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			final ContactList contactList = pageC.getContactList();
			
			for (Contact i : contactList.getList())
				System.out.println(i.getPseudo());
        	
			if (contactList.comparePseudo(me)==false) {
					okFrame.add(new JLabel("Your pseudo is already used ! Please enter a new one !"));
					
					//Display the window.
					okFrame.setSize(500, 100);
			        okFrame.setLocationRelativeTo(null);
			        okFrame.setVisible(true);
			        me.setPseudo(null);
			        //supprime le contact manager
			        pageC.setCm(null);
			        
			} else {
				
				okFrame.add(new JLabel("Welcome to the ChatSystem !"));
				//Display the window.
				okFrame.setSize(250, 100);
		        okFrame.setLocationRelativeTo(null);
		        okFrame.setVisible(true);
		        
		        Timer t = new Timer(500, new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	okFrame.setVisible(false);
		            	okFrame.dispose();
		            	connectionFrame.setVisible(false);
		            	connectionFrame.dispose();
		            	pageC.setMain(new MainMenu1(me, contactList,pageC.getCm()));
				        //stop la partie connection du contact manager
				        pageC.getCm().setRun_co(false); // la phase de connection est finie
		    			
		            }
		        });
		        t.setRepeats(false); // Only execute once
		        t.start();
			}
			
			
		} else if(pageM != null && event.getSource().equals(pageM.getChangepseudo())) {
			
			final JFrame modifyFrame = pageM.getModifyFrame();
			
	        //Display the window.
	        modifyFrame.setLocationRelativeTo(null); // au centre
	        modifyFrame.setVisible(true);
	        
			
		} else if (pageM != null && event.getSource().equals(pageM.getVerifyPseudo())) {
			

			final JFrame modifyFrame = pageM.getModifyFrame();
			
			ContactList contactList = pageM.getContactList();
			
			final JFrame okFrame = new JFrame("...");
			final String pseudo = pageM.getEnterpseudo().getText();
			Contact p = new Contact(pseudo);
			
			if (contactList.comparePseudo(p)==false) {
					okFrame.add(new JLabel("This username is already used ! Please enter a new one !"));
					
					//Display the window.
					okFrame.setSize(500, 100);
			        okFrame.setLocationRelativeTo(null);
			        okFrame.setVisible(true);
			        
			} else {
	
				okFrame.add(new JLabel("Success !"));
				//Display the window.
				okFrame.setSize(250, 100);
		        okFrame.setLocationRelativeTo(null);
		        okFrame.setVisible(true);
		        
		        Timer t = new Timer(500, new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	okFrame.setVisible(false);
		            	okFrame.dispose();
		            	modifyFrame.setVisible(false);
		    			pageM.getMe().setPseudo(pseudo);
		    			pageM.getPseudoLabel().setText(pageM.getMe().getPseudo());
		    			
		    			//envoyer son pseudo aux autres 
		    			ContactsManager cm = pageM.getCm();
		    			cm.setSendMe();
		            }
		        });
		        t.setRepeats(false); // Only execute once
		        t.start();
			}
			
			}
		
		else if (pageW != null && event.getSource().equals(pageW.getSendChat())) {
			//to do envoi du message
			
			JTextField chatInput = pageW.getChatInput();
			Message msg = new Message(pageW.getDest(), chatInput.getText());
			msg.printMsg(null);
			//to do envoi tcp au dest + affichage
			
		}
    }
	

}
