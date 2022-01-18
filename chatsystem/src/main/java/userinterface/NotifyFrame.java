package userinterface;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class NotifyFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Dimension size = new Dimension(300,100);

	public NotifyFrame(String Message) {
		super();
		this.setTitle("Notification");
		this.setSize(size);
	    this.setLocationRelativeTo(null);
	    this.getContentPane().add(new JLabel(Message));
	    
	}
	
	public void display() {
		this.setVisible(true);
		this.requestFocus();
	}

	public static void main(String[] args) {
		new NotifyFrame(null).display();
	}
}
