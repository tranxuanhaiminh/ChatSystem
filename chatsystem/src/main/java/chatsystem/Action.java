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
		if(pageC != null && (event.getSource().equals(pageC.getVerifyPseudo()) || event.getSource().equals(pageC.getEnterPseudo()))) {
			
			final Contact me = pageC.getMe();
			
			if (pageC.getEnterPseudo().getText()==null && !(pageC.getEnterPseudo().getText().equals(""))){
				
				pageC.getPseudoNull().display();
				
			} else {
	        	me.setPseudo(pageC.getEnterPseudo().getText());			
	
				//on crée et on lance le contacts manager
				pageC.getCm().setRunning(true);
				
				try {
					//on attend de finir de recevoir les contacts
					Thread.sleep(3001);
				
				} catch (InterruptedException e) {
					e.printStackTrace();
					pageC.getProblem().display();
				}
				
				pageC.getCm().setRunning(false);
				final ContactList contactList = pageC.getContactList();
				
				for (Contact i : contactList.getList())
					System.out.println(i.getPseudo());
	        	
				if (contactList.comparePseudo(me)==false) {
					
					//on arrête le contacts manager	
					pageC.getCm().setRunning(false);
			        
					pageC.getPseudoUsed().display();
			        
			        me.setPseudo(null);
			        
				        
				} else {
					
					pageC.getWelcome().display();
			        
			        Timer t = new Timer(500, new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			            	
			            	pageC.getWelcome().setVisible(false);
			            	
			            	pageC.setVisible(false);
			            	pageC.getCm().setState(); // mode Main
			            	pageC.getCm().setRunning(true);
			            	pageC.setMain(new MainMenu(me, contactList,pageC.getCm()));
			            	
			            }
			        });
			        t.setRepeats(false); // Only execute once
			        t.start();
				}
			}
			
		} else if(pageM != null && event.getSource().equals(pageM.getChangepseudo())) { //modif pseudo
			
	        //Display the window.
			pageM.getModifyFrame().setVisible(true);
	        
		} else if (pageM != null && (event.getSource().equals(pageM.getModifyFrame().getVerifyPseudo()) || event.getSource().equals(pageM.getModifyFrame().getEnterpseudo()))) {

			if (pageM.getModifyFrame().getEnterpseudo().getText() == null || pageM.getModifyFrame().getEnterpseudo().getText().equals("")) {
				
				pageM.getPseudoNull().display();
				
			} else {
				
				final String pseudo = pageM.getModifyFrame().getEnterpseudo().getText();
				Contact p = new Contact(pseudo,(String)null);
				ContactList contactList = pageM.getContactList();

				if (contactList.comparePseudo(p)==false) {
					
					pageM.getPseudoUsed().display();
				        
				} else {
					
					pageM.getModifSuccess().display();
			        pageM.getModifyFrame().getEnterpseudo().setText(null);

			        Timer t = new Timer(500, new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			            	
			            	pageM.getModifSuccess().setVisible(false);
			            	pageM.getModifyFrame().setVisible(false);
			    			pageM.getMe().setPseudo(pseudo);
			    			pageM.getPseudoLabel().setText(pageM.getMe().getPseudo());
			    			
			    			//envoyer son pseudo aux autres 
			    			ContactsManager cm = pageM.getCm();
			    			cm.signalDatagram(pageM.getMe().getPseudo(), "255.255.255.255");
			            }
			        });
			        t.setRepeats(false); // Only execute once
			        t.start();
				}
			}
			
		} else if (pageW != null && (event.getSource().equals(pageW.getSendChat()) || event.getSource().equals(pageW.getChatInput()))) {
			
			if (pageW.getConv() == null){
				
				pageW.getMain().getUserNotConnected().display();
				
			} else {
				JTextField chatInput = pageW.getChatInput();
				
				if (chatInput.getText() != null && !(chatInput.getText().equals(""))) {
			
					// creating the msg
					Message msg = new Message(pageW.getDest(), chatInput.getText());
					chatInput.setText(null);
					
					//afficher le message sur la page et le mettre dans la DB
					pageW.addChatLine(msg,true);
					
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
		
		if (indexr != -1 && indexc!=-1) {
			lsm.clearSelection();
			if (indexc >0) {
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
						
						new Conversation(pageM,dest);
						
					} else {
						System.out.println("Vous avez déjà cette conversation !\n");
						in.getChatw().requestFocus();
					}
					
		        } else {
		        	 System.out.println("Personne non connectée, on affiche la conversation\n");
		        	 try {
		        		 
		        		 new ChatWindow(pageM, new Contact(InetAddress.getByName(pseudo)), null);
		        		 
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
						this.pageM.getProblem().display();
					}
		        	 pageM.getUserNotConnected().display();
		        }
			}
		}
	}
	

}
