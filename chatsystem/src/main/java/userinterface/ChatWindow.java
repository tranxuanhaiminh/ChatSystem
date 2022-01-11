/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package userinterface;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

import chatsystem.Action;
import chatsystem.Contact;
import chatsystem.Conversation;
import chatsystem.Message;

/**
 *
 * @author Minh
 */
public class ChatWindow extends javax.swing.JFrame {

<<<<<<< HEAD
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String newline = "\n";
    private String msg_in = "";
    
    private javax.swing.JTextField chatInput;
    private javax.swing.JButton sendChat;
    private JScrollBar bar;

	private Contact dest;
	private Conversation conv;
	
	//lien page principale
	private MainMenu mm;
	
	//listeners
	private Action sendMess;
    
    /**
     * Creates new form NewJFrame
     */
    public ChatWindow(MainMenu m, Contact dest, Conversation conv) {
    	
        this.setTitle(dest.getPseudo());
        initComponents();

        this.dest = dest;
        this.mm = m;
		this.conv = conv;
		
		this.sendMess = new Action(this);

        chatInput = msg_input;
        chatInput.addActionListener(this.sendMess);

        sendChat = msg_send;
        sendChat.addActionListener(this.sendMess);
        
        javax.swing.JFrame frame = this; 
        this.addWindowListener(new WindowAdapter() {
			 
			 public void windowClosing(WindowEvent e) {
				 if (conv != null)
					 conv.stopConv();
			     frame.setVisible(false);
			     frame.dispose();
			 }
			  
		});
        
        JScrollBar bar = jScrollPane1.getVerticalScrollBar();
        bar.setValue(bar.getMaximum());
        
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

        msg_input = new javax.swing.JTextField();
        msg_send = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        msg_display = new javax.swing.JTextArea();

        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        msg_send.setText("Send");
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
        msg_display.setText("this can't be changed\nsdfsdf\nsdf\nsd\nf\nsdf\nsdf\nsd\nfs\ndf\nsdf\nsdf\nsd\nfsd\nfs\ndf\nsdf\nsd\nfsdfsdrgsdfgsdfgsdfgsdfgsdfgdsfgsdfgjfsdfsdrgsdfgsdfgsdfgsdfgsdfgdsfgsdfgjfsdfsdrgsdfgsdfgsdfgsdfgsdfgdsfgsdfgjfsdfsdrgsdfgsdfgsdfgsdfgsdfgdsfgsdfgj");
        jScrollPane1.setViewportView(msg_display);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(msg_input, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(msg_send, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(msg_send, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(msg_input, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void msg_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_sendActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_msg_sendActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea msg_display;
    private javax.swing.JTextField msg_input;
    private javax.swing.JButton msg_send;
    // End of variables declaration//GEN-END:variables

   

    public void addChatLine(Message chatline, boolean isMe) {
    	if (isMe) {
    		msg_display.append("Me : "+chatline.toString() + newline);
    		
    	} else {
    		msg_display.append(this.getDest().getPseudo()+" : "+chatline + newline);
    	}
    	bar.setValue(bar.getMaximum());
    	
    	//add the msg to dataabase
    }
    
    public Action getSendMess() {
=======
	/**
	 * Fields
	 */
	private final static String newline = "\n";
	private String msg_in = "";
	
	// Person in this chat
	private Contact dest;

	// listeners
	private Action sendMess;

	/**
	 * Creates new form NewJFrame
	 */
	public ChatWindow(String title) {
		this.setTitle(title);
		initComponents();
	}
	
	public ChatWindow(Contact dest) {
		initComponents();
		this.dest = dest;
		this.sendMess = new Action(this);
		this.setTitle(dest.getPseudo());
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		msg_input = new javax.swing.JTextField();
		msg_send = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		msg_display = new javax.swing.JTextArea();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		msg_send.setText("Send");
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
		msg_display.setText(
				"this can't be changed\nsdfsdf\nsdf\nsd\nf\nsdf\nsdf\nsd\nfs\ndf\nsdf\nsdf\nsd\nfsd\nfs\ndf\nsdf\nsd\nfsdfsdrgsdfgsdfgsdfgsdfgsdfgdsfgsdfgjfsdfsdrgsdfgsdfgsdfgsdfgsdfgdsfgsdfgjfsdfsdrgsdfgsdfgsdfgsdfgsdfgdsfgsdfgjfsdfsdrgsdfgsdfgsdfgsdfgsdfgdsfgsdfgj");
		jScrollPane1.setViewportView(msg_display);

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

		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * Methods
	 */
	private void msg_sendActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_msg_sendActionPerformed
		// TODO add your handling code here:
		msg_in = msg_input.getText();
		msg_input.setText("");

	}// GEN-LAST:event_msg_sendActionPerformed

	public void addChatLine(String chatline) {
		msg_display.append(chatline + newline);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ChatWindow("JframeTitle").setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea msg_display;
	private javax.swing.JTextField msg_input;
	private javax.swing.JButton msg_send;
	// End of variables declaration//GEN-END:variables
	
	/**
	 * Methods
	 */
	public Action getSendMess() {
>>>>>>> 75b836d5eac2188f26c9bd1eb358d41dd668eee7
		return this.sendMess;
	}
	
	public JTextField getChatInput() {
<<<<<<< HEAD
		return chatInput;
=======
		return msg_input;
>>>>>>> 75b836d5eac2188f26c9bd1eb358d41dd668eee7
	}
	
	public Contact getDest() {
		return dest;
	}
	public JButton getSendChat() {
<<<<<<< HEAD
		return sendChat;
	}
	public MainMenu getMm() {
		return mm;
	}

	public Conversation getConv() {
		return conv;
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
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatWindow(null,new Contact("titi"," "), null).setVisible(true);
            }
        });
        
    }
=======
		return msg_send;
	}
>>>>>>> 75b836d5eac2188f26c9bd1eb358d41dd668eee7
}
