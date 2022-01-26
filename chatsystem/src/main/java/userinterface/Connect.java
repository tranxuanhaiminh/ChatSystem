/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package userinterface;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

import ressources.Interfacedisplay;
import service.Action;

/**
 *
 * @author Minh
 */
public class Connect extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	private Action verify;
	
	/**
	 * Creates new form Connect
	 */
    public Connect(String buttonText) {
        initComponents(buttonText);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
//    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents(String buttonText) {
    	setTitle(Interfacedisplay.connecttitle);

    	enterPseudo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        buttonSubmit = new javax.swing.JButton();
        
        setResizable(false);

        jLabel1.setText(Interfacedisplay.usernamelabel);

		verify = new Action();
		
        buttonSubmit.setText(buttonText);
        buttonSubmit.setName("buttonSubmit");
        buttonSubmit.addActionListener(verify);

        enterPseudo.setName("enterPseudo");
        enterPseudo.addActionListener(verify);
        
        getRootPane().setDefaultButton(buttonSubmit);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(enterPseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(128, Short.MAX_VALUE)
                .addComponent(buttonSubmit)
                .addContainerGap(128, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(enterPseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(buttonSubmit)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        
        // Exit program if the user haven't set username
        addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				if (buttonText == Interfacedisplay.connectbutton) {					
					System.exit(0);
				}
				dispose();
			}
		});

        pack();
        
        this.setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // End of variables declaration//GEN-END:variables// Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton buttonSubmit;
    private javax.swing.JLabel jLabel1;
    private static javax.swing.JTextField enterPseudo;
    // End of variables declaration//GEN-END:variables
	
	/* Getters */
	public static JTextField getEnterPseudo() {
		return enterPseudo;
	}

	public static JButton getbuttonSubmit() {
		return buttonSubmit;
	}
}
