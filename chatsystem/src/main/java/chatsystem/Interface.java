package chatsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Interface implements ActionListener{
	
	private List pseudoList=new List();
	private String mypseudo;
	JTextField enterpseudo;
	
	
	public Interface() {
        //Create and set up the window.
        JFrame connectionFrame = new JFrame("Connect to the Chat System");
        connectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //récuperer la taille de l'écran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        //taille est un demi la longueur et l'hauteur
        connectionFrame.setSize(new Dimension(width/2, height/2));
        
        //Create and set up the panel.
        JPanel connectionPanel = new JPanel(new GridLayout(2, 1));
        
        //widgets
        enterpseudo = new JTextField();
        enterpseudo.setSize(new Dimension(10, 100));
        
        
        JButton verifyPseudo = new JButton("Connect");
        verifyPseudo.addActionListener(this);
        
        connectionPanel.add(enterpseudo);
        connectionPanel.add(verifyPseudo);
        
        //Add the panel to the window.
        connectionFrame.getContentPane().add(connectionPanel, BorderLayout.CENTER);
        
        //Display the window.
        connectionFrame.setLocationRelativeTo(null); // au centre
        connectionFrame.setVisible(true);
        
	}
	
	public void actionPerformed(ActionEvent event) {
		JFrame okFrame = new JFrame("ok");
		
		mypseudo = enterpseudo.getText();
		
		int n = pseudoList.size();
		for (int i; i<n; i++)
			
		
      //Display the window.
		okFrame.setSize(150, 100);
        okFrame.setLocationRelativeTo(null);
        okFrame.setVisible(true);
    }

	private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        Interface connection = new Interface();
    }
	
	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
