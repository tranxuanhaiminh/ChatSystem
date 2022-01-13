/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package userinterface;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import chatsystem.Action;
import chatsystem.Contact;
import chatsystem.ContactList;
import chatsystem.ContactsManager;
import ressources.Interfacedisplay;

/**
 *
 * @author Minh
 */
public class Connect extends javax.swing.JFrame {

    /**
     * Creates new form Connect
     */

    private javax.swing.JLabel labelUsername;
    private javax.swing.JTextField enterpseudo;
    private javax.swing.JButton verifyPseudo;
    
    //Notification Frames
    private NotifyFrame pseudoUsed;
    private NotifyFrame welcome;
    private NotifyFrame pseudoNull;
    private NotifyFrame alreadyRunning;

	private Action verify;
	
	private ContactList contactList;
	private Contact me;
	
	// recevoir les contacts et les mettre dans la liste des contacts
	private ContactsManager cm;
	
	//page principale
	private MainMenu main;
	
    public Connect() {
    	this.setTitle(Interfacedisplay.connecttitle);
        initComponents();
        this.setLocationRelativeTo(null);
        
      ///////////////NotifyFrames 
	  this.pseudoUsed = new NotifyFrame("This username is already used ! Try again");
	  this.welcome = new NotifyFrame("Welcome to the Chat System !");
	  this.pseudoNull = new NotifyFrame("Please enter a value");
	  this.alreadyRunning = new NotifyFrame("The program is already running !\nPlease close this session !");
	  this.alreadyRunning.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
		contactList = new ContactList();
		me =new Contact();
		
		// initialisation du contacts manager
		cm = new ContactsManager(this);
		cm.start();
		
		this.verify = new Action(this);
		
		// WTF /////////////////////////////////////
        labelUsername = jLabel1;
        labelUsername.setText("Username");

        verifyPseudo = jButton1;
        verifyPseudo.setText("Connect");
        verifyPseudo.addActionListener(this.verify);

        enterpseudo = jTextField1;
        enterpseudo.setText("");
        enterpseudo.addActionListener(this.verify);
        /////////////////////////////////////////////
        
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

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel1.setText(Interfacedisplay.usernamelabel);

        jButton1.setText(Interfacedisplay.connectbutton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(128, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap(128, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jButton1)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    // End of variables declaration//GEN-END:variables// Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
	
    
    /////////////////////////////////////////////////////////////
	public Contact getMe() {
		return me;
	}

	public ContactList getContactList() {
		return contactList;
	}
	
	public JTextField getEnterPseudo() {
		return enterpseudo;
	}

	public JFrame getConnectionFrame() {
		return this;
	}

	public JButton getVerifyPseudo() {
		return verifyPseudo;
	}

	public ContactsManager getCm() {
		return cm;
	}
	
	public void setCm(ContactsManager m) {
		cm = m;
	}

	public MainMenu getMain() {
		return main;
	}

	public void setMain(MainMenu main) {
		this.main = main;
	}
	
	
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
            java.util.logging.Logger.getLogger(Connect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Connect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Connect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Connect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Connect();
            }
        });
    }

	public NotifyFrame getPseudoUsed() {
		return pseudoUsed;
	}

	public NotifyFrame getWelcome() {
		return welcome;
	}

	public NotifyFrame getPseudoNull() {
		return pseudoNull;
	}

	public NotifyFrame getAlreadyRunning() {
		return alreadyRunning;
	}
}
