package chatsystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.Timer;
import userinterface.ChatWindow;
import userinterface.Connect;
import userinterface.MainMenu;


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
			
			final Contact me = pageC.getMe();
			
			if (pageC.getEnterPseudo().getText()==null && !(pageC.getEnterPseudo().getText().equals(""))){
				pageC.getPseudoNull().setVisible(true);
				
			} else {
	        	me.setPseudo(pageC.getEnterPseudo().getText());			
	
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
	
					/*final JFrame okFrame = new JFrame("Connecting....");
					okFrame.add(new JLabel("Your pseudo is already used ! Please enter a new one !"));
					
					//Display the window.
					okFrame.setSize(500, 100);
			        okFrame.setLocationRelativeTo(null);
			        okFrame.setVisible(true);*/
			        
					pageC.getPseudoUsed().display();
			        
			        me.setPseudo(null);
			        
				        
				} else {
	
					/*final JFrame okFrame = new JFrame("Connecting....");
					okFrame.add(new JLabel("Welcome to the ChatSystem !"));
					//Display the window.
					okFrame.setSize(250, 100);
			        okFrame.setLocationRelativeTo(null);
			        okFrame.setVisible(true);*/
					
					pageC.getWelcome().display();
			        
			        Timer t = new Timer(500, new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			            	/*okFrame.setVisible(false);
			            	okFrame.dispose();*/
			            	pageC.getWelcome().setVisible(false);
			            	
			            	pageC.setVisible(false);
			            	pageC.getCm().setState(); // mode Main
			            	pageC.getCm().setRunning(true);
			            	pageC.setMain(new MainMenu(me, contactList,pageC.getCm()));
			            	
			            	pageC.dispose();
			            }
			        });
			        t.setRepeats(false); // Only execute once
			        t.start();
				}
			}
			
		} else if(pageM != null && event.getSource().equals(pageM.getChangepseudo())) { //modif pseudo
			
	        //Display the window.
			pageM.getModifyFrame().setVisible(true);
	        
		} else if (pageM != null && event.getSource().equals(pageM.getModifyFrame().getVerifyPseudo())) {

			if (pageM.getModifyFrame().getEnterpseudo().getText() == null || pageM.getModifyFrame().getEnterpseudo().getText().equals("")) {
				
				pageM.getPseudoNull().display();
				
			} else {
				
				final String pseudo = pageM.getModifyFrame().getEnterpseudo().getText();
				Contact p = new Contact(pseudo,(String)null);
				ContactList contactList = pageM.getContactList();

				if (contactList.comparePseudo(p)==false) {

					/*final JFrame okFrame = new JFrame("...");
					okFrame.add(new JLabel("This username is already used ! Please enter a new one !"));
					
					//Display the window.
					okFrame.setSize(500, 100);
			        okFrame.setLocationRelativeTo(null);
			        okFrame.setVisible(true);*/
					pageM.getPseudoUsed().display();
					
				        
				} else {
		
					/*
					 * final JFrame okFrame = new JFrame("...");
					 * okFrame.add(new JLabel("Success !"));
					//Display the window.
					okFrame.setSize(250, 100);
			        okFrame.setLocationRelativeTo(null);
			        okFrame.setVisible(true);*/
					
					pageM.getModifSuccess().display();
			        pageM.getModifyFrame().getEnterpseudo().setText(null);

			        Timer t = new Timer(500, new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			            	/*okFrame.setVisible(false);
			            	okFrame.dispose();*/
			            	
			            	pageM.getModifSuccess().setVisible(false);
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
				/*final JFrame okFrame = new JFrame("...");
				okFrame.setSize(new Dimension(250,100));
		        okFrame.setLocationRelativeTo(null);
				okFrame.add(new JLabel("This user is not connected !"));
				okFrame.setVisible(true);*/
				pageW.getMain().getUserNotConnected().display();
				
			} else {
				JTextField chatInput = pageW.getChatInput();
				
				if (chatInput.getText() != null && !(chatInput.getText().equals(""))) {
			
					// creating the msg
					Message msg = new Message(pageW.getDest(), chatInput.getText());
					chatInput.setText(null);
					
					//afficher le message sur la page
					pageW.addChatLine(msg,true);
					
					//ajouter à la base de données
					
					//utiliser le contact manager
					pageW.getMain().getMessMan().signalMess(pageW.getConv(), msg);
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
		        Contact dest = pageM.getContactList().findP(pseudo);
		        
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
		        	 try {
		        		 
		        		 new ChatWindow(pageM, new Contact(InetAddress.getByName(pseudo)), null);
		        		 
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	 pageM.getUserNotConnected().display();
		        }
			}
		}
	}
	

}
