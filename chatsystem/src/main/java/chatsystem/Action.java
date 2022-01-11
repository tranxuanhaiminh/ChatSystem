package chatsystem;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import userinterface.ChatWindow;
import userinterface.Connect;
import userinterface.MainMenu;
import userinterface.Modify;


public class Action implements ActionListener, ListSelectionListener{
	
	public Connect pageC;
	public MainMenu pageM;
	public ChatWindow pageW;
	
	public Action(Connect c){
		super();
		this.pageC = c;
	}
	
	public Action(MainMenu m)  {
		super();
		this.pageM = m;
	}
	
	public Action(ChatWindow c) {
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

			//on crÃ©e et on lance le contact manager
			pageC.getCm().setRunning(true);
			
			try {
				//on attend de finir de recevoir les contacts
				Thread.sleep(3300);
			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			pageC.getCm().setRunning(false);
			final ContactList contactList = pageC.getContactList();
			
			for (Contact i : contactList.getList())
				System.out.println(i.getPseudo());
        	
			if (contactList.comparePseudo(me)==false) {
				//on arrÃªte le contact manager	
				pageC.getCm().setRunning(false);

				okFrame.add(new JLabel("Your pseudo is already used ! Please enter a new one !"));
				
				//Display the window.
				okFrame.setSize(500, 100);
		        okFrame.setLocationRelativeTo(null);
		        okFrame.setVisible(true);
		        me.setPseudo(null);
		        
			        
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
		            	pageC.getCm().setState(); // mode Main
		            	pageC.getCm().setRunning(true);
		            	pageC.setMain(new MainMenu(me, contactList,pageC.getCm()));
		            }
		        });
		        t.setRepeats(false); // Only execute once
		        t.start();
			}
			
			
		} else if(pageM != null && event.getSource().equals(pageM.getChangepseudo())) { //modif pseudo
			
			final JFrame modifyFrame = pageM.getModifyFrame();
			
	        //Display the window.
	        modifyFrame.setLocationRelativeTo(null); // au centre
	        modifyFrame.setVisible(true);
	        
		} else if (pageM != null && event.getSource().equals(pageM.getModifyFrame().getVerifyPseudo())) {
			final Modify modifyFrame = pageM.getModifyFrame();

			if (modifyFrame.getEnterpseudo().getText() == null) {
				JFrame okFrame = new JFrame("...");
				okFrame.add(new JLabel("Please enter a value"));
				okFrame.setVisible(true);
			} else {
				final JFrame okFrame = new JFrame("...");
				final String pseudo = pageM.getModifyFrame().getEnterpseudo().getText();
				Contact p = new Contact(pseudo);
				ContactList contactList = pageM.getContactList();

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
			        pageM.getModifyFrame().getEnterpseudo().setText(null);

			        Timer t = new Timer(500, new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			            	okFrame.setVisible(false);
			            	okFrame.dispose();
			            	pageM.getModifyFrame().setVisible(false);
			    			pageM.getMe().setPseudo(pseudo);
			    			pageM.getPseudoLabel().setText(pageM.getMe().getPseudo());
			    			
			    			//envoyer son pseudo aux autres 
			    			ContactsManager cm = pageM.getCm();
			    			/*UDPSender cs= cm.getContactSender();
	    					cs= new UDPSender(pageM.getMe().getPseudo(), "255.255.255.255");
							cs.send();*/
			    			cm.signalDatagram(pageM.getMe().getPseudo(), "255.255.255.255");
			            }
			        });
			        t.setRepeats(false); // Only execute once
			        t.start();
				}
			}
			
		} else if (pageW != null && (event.getSource().equals(pageW.getSendChat()) || event.getSource().equals(pageW.getChatInput()))) {
			
			if (pageW.getConv() == null){
				final JFrame okFrame = new JFrame("...");
				okFrame.setSize(new Dimension(250,100));
		        okFrame.setLocationRelativeTo(null);
				okFrame.add(new JLabel("This user is not connected !"));
				okFrame.setVisible(true);
				
			} else {
				JTextField chatInput = pageW.getChatInput();
				
				if (chatInput != null) {
					// creating the msg
					Message msg = new Message(pageW.getDest(), chatInput.getText());
					chatInput.setText(null);
					
					//afficher le message sur la page
					pageW.addChatLine(msg,true);
					
					//ajouter à la base de données
					
					//utiliser le contact manager
					pageW.getMm().getMessMan().signalMess(pageW.getConv(), msg);
				}
			}
			
		}
    }

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		ListSelectionModel lsm = (ListSelectionModel)e.getSource();
		
		int indexr = pageM.getPseudosList().getSelectedRow();
		int indexc = pageM.getPseudosList().getSelectedColumn();
		//System.out.println(indexr+" "+indexc);
		
		if (indexr != -1 && indexc!=-1) {
			lsm.clearSelection();
			if (indexc >0) {
				System.out.println("Starting a new chat session\n");
				
		        String pseudo = (String) pageM.getPseudosList().getValueAt(indexr, indexc);
		        Contact dest = pageM.getContactList().existsP(pseudo);
		        
				if (dest!=null) {
					Conversation in=null;
					for (Conversation c : pageM.getMessMan().getConvList()) {
						if (c.getInterlocutor().equals(dest)){
							in = c;
						}
					}
					if (in==null) {
						System.out.println("Contact trouvé, on lance la conversation\n");
						Conversation c = new Conversation(pageM,dest);
						pageM.getMessMan().getConvList().add(c);
					} else {
						System.out.println("Vous avez déjà cette conversation !\n");
						in.getChatw().requestFocus();
					}
					
				
		        } else {
		        	 System.out.println("Contact non connecté, on affiche la conversation\n");
		        	 new ChatWindow(pageM, new Contact(pseudo), null);
		        }
			}
		}
	}
	

}
