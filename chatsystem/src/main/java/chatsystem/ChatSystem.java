package chatsystem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import network.IpAddress;
import network.UDPReceive;
import network.UDPSend;
import ressources.Interfacedisplay;
import service.DbService;
import userinterface.Connect;

public class ChatSystem {
	
	/* Properties */
	public static ExecutorService threadpool;

	/* main */
	public static void main(String args[]) {
		// Github token : g h p _ L1pKL0tN7ZtvdVvEaJSeOjuVveU7qs2Jjnpa
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
			java.util.logging.Logger.getLogger(Connect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Connect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Connect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Connect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		// Initiate threadpool
		threadpool = Executors.newCachedThreadPool();
		
		/* Set the ip and broadcast addresses of this machine that is used in the chatsystem */
		IpAddress.getAddresses();
		;		
		/* Start database connection */
		DbService.dbInit();
		
		/* Start listening to UDP packet */
		new UDPReceive();
		
		/* Ask for contacts */
		UDPSend.send("ASK", IpAddress.getBroadcast());

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Connect(Interfacedisplay.connectbutton);
			}
		});
	}
}
