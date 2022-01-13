/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package userinterface;

import javax.swing.ListSelectionModel;

import chatsystem.Action;
import chatsystem.Contact;
import chatsystem.ContactList;
import chatsystem.ContactsManager;
import chatsystem.MessagesManager;
import database.Databasecon;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Minh
 */
public class MainMenu extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ContactList contactList;
	private Contact me;
	
	private final String disconnected = "Images/gray.png";
	private final String connected = "Images/green.png";
	
	private Modify modifyFrame;
    private javax.swing.JButton changepseudo;
    private javax.swing.JLabel pseudoLabel;
    private javax.swing.JTable pseudosList;
    
    // Notification frames 
    private NotifyFrame userNotConnected;
    private NotifyFrame pseudoUsed;
    private NotifyFrame pseudoNull;
    private NotifyFrame modifSuccess;
	
    //gestion des contacts
	private ContactsManager cm;

	//gestions des messages
	private MessagesManager messMan;
	
	//Base de donn�es
	private Databasecon conDB;
	
	
    /**
     * Creates new form MainMenu
     */
    public MainMenu(Contact m, ContactList l, ContactsManager cm) {

    	this.setTitle("ChatSystem");
        initComponents();
        this.setLocationRelativeTo(null);
        
		/////////////////// Notify Frames
		        
		this.pseudoNull = new NotifyFrame("Please enter a value");
		this.modifSuccess= new NotifyFrame("Succes !");
		this.pseudoUsed = new NotifyFrame("This username is already used ! Try again !");
		this.userNotConnected = new NotifyFrame("This user is not connected ! You can't send messages !");

        
        this.conDB = new Databasecon("datbase.db");
        
		this.me = m; 
		this.contactList = l;
		
		pseudoLabel = jLabel1;
		pseudoLabel.setText(me.getPseudo());
		
		//Envoyer et recevoir des messages 
		this.messMan = new MessagesManager(this);
		this.messMan.start();
		
		//gestion des contacts
		this.cm = cm;
		
        modifyFrame = new Modify();
        modifyFrame.getVerifyPseudo().addActionListener(new Action(this));
        
        changepseudo = jButton1;
        changepseudo.setText("Modify");
        changepseudo.addActionListener(new Action(this));
        
        pseudosList = jTable1;
        pseudosList.setCellSelectionEnabled(true);
        pseudosList.setColumnSelectionInterval(1, 1);
        pseudosList.setColumnSelectionAllowed(false);
        pseudosList.setRowSelectionAllowed(false);
        pseudosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel listSelectionModel = pseudosList.getSelectionModel();
	    listSelectionModel.addListSelectionListener(new Action(this));
	    
        this.addWindowListener(new WindowAdapter() {
			 
			 public void windowClosing(WindowEvent e) {
				 
				 // Sending a disconnected msg
				 System.out.println("\nDISCONNECTING ...\n");
				 if (cm!=null) {
					 cm.signalDatagram("DISCONNECTED","255.255.255.255");
				 }
				 // Stopping the Contact manager
				 if (cm!=null)
					 cm.setRunning(false);
				 //Stopping the message manager
				 if (messMan!=null)
					 messMan.setRunning(false);
				 
			     System.exit(0);
			  
			 }
			  
			   });
        
        
        ///////////////adding the users
        
        for (Contact c : this.contactList.getList()) {
        	addUser(c.getPseudo(), connected);
        }
        
        addUser("user1", connected);
        addUser("user2", disconnected);
        
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText(";sadkjfa;sadkjfasdf");

        jButton1.setText("Modify");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Status", "Username"
            }
        ) {
            public Class getColumnClass(int column) {
    			return getValueAt(0, column).getClass();
    		}
            
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(20);
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(45);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(45);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(45);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(1000);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    
    public void addUser(String username, String img) {
        DefaultTableModel model = (DefaultTableModel) pseudosList.getModel();
        model.addRow(new Object[]{new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(10,10,java.awt.Image.SCALE_SMOOTH)), username});
    }
    
    public boolean modUser(String newname, String img, String oldname ) {
    	DefaultTableModel model = (DefaultTableModel) pseudosList.getModel();
    	boolean res = false;
    	int n = model.getRowCount();
    	for (int i=0; i<n;i++) {
    		if (oldname.equals(model.getValueAt(i, 1))) {
    			model.setValueAt(newname, i, 1);
    			model.setValueAt(new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(10,10,java.awt.Image.SCALE_SMOOTH)), i, 0);
    			res = true;
    		}
    	}
    	return res;
    }
    
    
    
    
    public Contact getMe() {
		return me;
	}
	
	public ContactList getContactList() {
		return contactList;
	}

	public javax.swing.JButton getChangepseudo() {
		return changepseudo;
	}

	public Modify getModifyFrame() {
		return modifyFrame;
	}
	
	public javax.swing.JLabel getPseudoLabel() {
		return pseudoLabel;
	}
	
	public ContactsManager getCm() {
		return cm;
	}
	
	public MessagesManager getMessMan() {
		return this.messMan;
	}

	public javax.swing.JTable getPseudosList() {
		return pseudosList;
	}
	
	public String getConnected() {
		return connected;
	}

	public String getDisconnected() {
		return disconnected;
	}

	public Databasecon getConDB() {
		return conDB;
	}
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	
            	ContactList cl = new ContactList();
        		
        		cl.addContact(new Contact("titi","LaptopMari�tou"));

        		Contact me = new Contact("toto","127.0.0.1");
        		ContactsManager cm=null;
        		
        		new MainMenu(me, cl, cm);
            }
        });
    }

	public NotifyFrame getUserNotConnected() {
		return userNotConnected;
	}

	public NotifyFrame getPseudoUsed() {
		return pseudoUsed;
	}

	public NotifyFrame getPseudoNull() {
		return pseudoNull;
	}

	public NotifyFrame getModifSuccess() {
		return modifSuccess;
	}

}
