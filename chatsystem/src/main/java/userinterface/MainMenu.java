/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package userinterface;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;

import chatsystem.Action;
import chatsystem.Contact;
import chatsystem.ContactList;
import chatsystem.ContactsManager;
import chatsystem.MessagesManager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Minh
 */
public class MainMenu extends javax.swing.JFrame {

	private ContactList contactList;
	private Contact me;
	
	private ContactsManager cm=null;

	//gestions des messages
	private MessagesManager messMan;
	
    /**
     * Creates new form MainMenu
     */
    public MainMenu(Contact m, ContactList l, ContactsManager cm) {

		this.me = m; 
		this.contactList = l;
		
		//Envoyer et recevoir des messages 
		this.messMan = new MessagesManager(this);
		this.messMan.start();
        initComponents();
        addUser("user1", "Images/green.png");
        addUser("user2", "Images/gray.png");
        
        changepseudo.addActionListener(new Action(this));
        verifyPseudo.addActionListener(new Action(this));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pseudoLabel = new javax.swing.JLabel();
        changepseudo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        pseudosList = new javax.swing.JTable();
        pseudosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
		ListSelectionModel listSelectionModel = pseudosList.getSelectionModel();
	    listSelectionModel.addListSelectionListener(new Action(this));
	    
        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
        
        pseudoLabel.setText(me.getPseudo());

        changepseudo.setText("Modify");
        
        pseudosList.setModel(new javax.swing.table.DefaultTableModel(
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
        pseudosList.setRowHeight(20);
        pseudosList.setShowGrid(true);
        jScrollPane1.setViewportView(pseudosList);
        if (pseudosList.getColumnModel().getColumnCount() > 0) {
            pseudosList.getColumnModel().getColumn(0).setMinWidth(45);
            pseudosList.getColumnModel().getColumn(0).setPreferredWidth(45);
            pseudosList.getColumnModel().getColumn(0).setMaxWidth(45);
            pseudosList.getColumnModel().getColumn(1).setResizable(false);
            pseudosList.getColumnModel().getColumn(1).setPreferredWidth(1000);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pseudoLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(changepseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pseudoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(changepseudo, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addContainerGap())
        );
        
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        
       /* modifyFrame = new javax.swing.JFrame("Modify your username"); 
        enterpseudo = new javax.swing.JTextField();
        verifyPseudo = new javax.swing.JButton();
        getVerifyPseudo().addActionListener(new Action(this));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        enterpseudo.setText("");
        verifyPseudo.setText("Modify");

        javax.swing.GroupLayout layout2 = new javax.swing.GroupLayout(modifyFrame.getContentPane());
        getContentPane().setLayout(layout2);
        layout2.setHorizontalGroup(
        		layout2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout2.createSequentialGroup()
                .addGap(15, 15, 15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(enterpseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout2.createSequentialGroup()
                .addContainerGap(128, Short.MAX_VALUE)
                .addComponent(verifyPseudo)
                .addContainerGap(128, Short.MAX_VALUE))
        );
        layout2.setVerticalGroup(
        		layout2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout2.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enterpseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(verifyPseudo)
                .addContainerGap(16, Short.MAX_VALUE))
        );*/

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
                new MainMenu(new Contact("toto",""),null,null).setVisible(true);
            }
        });
    }
    
    public void addUser(String username, String img) {
        DefaultTableModel model = (DefaultTableModel) pseudosList.getModel();
        model.addRow(new Object[]{new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(10,10,java.awt.Image.SCALE_SMOOTH)), username});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changepseudo;
    private javax.swing.JLabel pseudoLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable pseudosList;
    // End of variables declaration//GEN-END:variables
    
    private javax.swing.JFrame modifyFrame;    
    private javax.swing.JButton verifyPseudo;
    private javax.swing.JTextField enterpseudo;


    
    public Contact getMe() {
		return me;
	}
	
	
	public ContactList getContactList() {
		return contactList;
	}

	public javax.swing.JButton getChangepseudo() {
		return changepseudo;
	}

	public javax.swing.JFrame getModifyFrame() {
		return modifyFrame;
	}

	public javax.swing.JButton getVerifyPseudo() {
		return verifyPseudo;
	}

	public javax.swing.JTextField getEnterpseudo() {
		return enterpseudo;
	}
	
	public javax.swing.JLabel getPseudoLabel() {
		return pseudoLabel;
	}
	
	public ContactsManager getCm() {
		return cm;
	}
	
	/*public JFrame getStartingChat() {
		return startingChat;
	}
	
	public JButton getYesB() {
		return yesB;
	}*/
	
	public MessagesManager getMessMan() {
		return this.messMan;
	}

	public javax.swing.JTable getPseudosList() {
		return pseudosList;
	}
	

}
