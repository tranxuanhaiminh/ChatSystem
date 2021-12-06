package chatsystem;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Action implements ActionListener{
	
	public Connection pageC;
	public MainMenu pageM;
	
	public Action(Connection c){
		super();
		this.pageC = c;
	}
	
	public Action(MainMenu m)  {
		super();
		this.pageM = m;
	}

	public void actionPerformed(ActionEvent event) {
		
		final JFrame modifyFrame = pageM.getModifyFrame();
		if(event.getSource() == pageM.getChangepseudo()) {
			
	        //Display the window.
	        modifyFrame.setLocationRelativeTo(null); // au centre
	        modifyFrame.setVisible(true);
	        
			
		} else if (event.getSource() == pageM.getVerifyPseudo()) {
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
		            }
		        });
		        t.setRepeats(false); // Only execute once
		        t.start();
			}
			
			}
    }
	

}
