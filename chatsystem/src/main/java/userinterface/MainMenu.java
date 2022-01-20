/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package userinterface;

import javax.swing.ListSelectionModel;

import chatsystem.Action;
import chatsystem.Contact;
import chatsystem.ContactList;
//import chatsystem.ContactsManager;
import chatsystem.MessagesManager;
import network.IpAddress;
import network.UDPSend;
import ressources.Interfacedisplay;

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
	
	private final String disconnected = "Images/gray.png";
	private final String connected = "Images/green.png";
	
	private Connect modifyFrame;
	
//	private ContactsManager cm;

	private MessagesManager messMan;
	
	
    /**
     * Creates new form MainMenu
     */
    public MainMenu() {

    	this.setTitle(Interfacedisplay.mainmenutitle);
        initComponents();
        this.setLocationRelativeTo(null);
		
		jLabel1.setText(ContactList.getMe().getPseudo());
		
		//Envoyer et recevoir des messages 
		this.messMan = new MessagesManager(this);
		this.messMan.start();
		
		//gestion des contacts
//		this.cm = cm;
//		this.cm.setState();
//		this.cm.setRunning(true);
		
        modifyFrame = new Connect(Interfacedisplay.modifybutton);

        jButton1.setText(Interfacedisplay.modifybutton);
        jButton1.addActionListener(new Action());
        
        jTable1.setColumnSelectionInterval(1, 1);
        jTable1.setCellSelectionEnabled(true);
        jTable1.setColumnSelectionAllowed(false);
        jTable1.setRowSelectionAllowed(false);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel listSelectionModel = jTable1.getSelectionModel();
	    listSelectionModel.addListSelectionListener(new Action());
        
//        addUser("abc", connected);
//        addUser("xyz", disconnected);
//        
//        System.out.println(((ImageIcon)jTable1.getModel().getValueAt(1, 0)).getDescription());
	    
        this.addWindowListener(new WindowAdapter() {
			 
			 public void windowClosing(WindowEvent e) {
				 
				 //Telling everybody that we are disconnecting
				 UDPSend.send("DC", IpAddress.getBroadcast());
//				 System.out.println("DISCONNECTING ...\n");
//				 if (cm!=null) {
//					 cm.signalDatagram("DISCONNECTED","255.255.255.255");
//				 }
//				 
//				 // Stopping the Contact manager
//				 if (cm!=null)
//					 cm.setRunning(false);
//				 
//				 //Stopping the message manager
//				 if (messMan!=null)
//					 messMan.setRunning(false);
				 
				 //Stopping the program
			     System.exit(0);
			  
			 }
			  
			   });
        
        
        ///////////////adding the users to the connected users' table
        for (Contact c : ContactList.getList()) {
        	addUser(c.getPseudo(), connected);
        }
        
        setVisible(true);
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

        jButton1.setText("Modify");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
            		Interfacedisplay.tablestatuscol, Interfacedisplay.tableusercol
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
    private static javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    
    public void addUser(String username, String img) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        ImageIcon imgicon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(10,10,java.awt.Image.SCALE_SMOOTH));
        imgicon.setDescription(img);
        model.addRow(new Object[]{imgicon, username});
    }
    
    public int isInTable(String s) {
    	DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    	for (int i=0; i<model.getRowCount();i++) {
    		if (s.equals(model.getValueAt(i, 1)))
    			return i;
    	}
    	return -1;
    }
    
    public void removeUser(int i) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.removeRow(i);
    }
    
    public boolean modUser(String newname, String img, String oldname ) {
    	DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
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
	
	public MessagesManager getMessMan() {
		return this.messMan;
	}
	
	public static void setUsernameLabel(String pseudo) {
		jLabel1.setText(pseudo);
	}

}