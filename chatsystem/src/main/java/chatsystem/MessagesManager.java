package chatsystem;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import userinterface.Alert;
import userinterface.MainMenu;

public class MessagesManager extends Thread { // chaque conversation est g�r� par un Msgsender et un Msgreceiver

	private MainMenu main;
	private boolean running;
	private int port = 55555;
	private ServerSocket ss;

	private ArrayList<Conversation> ConvList;
	private ArrayList<Conversation> stoppedConvList;

	private Conversation c = null;
	private Message m = null;

	public MessagesManager(MainMenu mainMenu) {
		super();
		this.main = mainMenu;
		this.running = true;
		this.ConvList = new ArrayList<Conversation>();
		this.stoppedConvList = new ArrayList<Conversation>();

		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			new Alert("Error : Please close the program!\n").setVisible(true);
		}
	}

	public void run() {

		new Thread(new Runnable() {
			@Override
			public void run() {

				Socket doorbell = null;
				while (running) {

					try {
						doorbell = ss.accept();
					} catch (IOException e) {
						e.printStackTrace();
						new Alert("Error : Please close the program!\n").setVisible(true);

					}

					InetAddress host = doorbell.getInetAddress();
					Conversation ec = null;
					for (Conversation withknownhost : ConvList) {
						if (host.equals(withknownhost.getInterlocutor().getIpaddress())) {
							System.out.println("La conversation a �t� trouv�.\n");
							ec = withknownhost;
						}
					}

					Conversation stopped = null;
					for (Conversation withknownhost : stoppedConvList) {
						if (host.equals(withknownhost.getInterlocutor().getIpaddress())) {
							System.out.println("La conversation a était dans les conv arrêtées.\n");
							stopped = withknownhost;
						}
					}

					final Conversation encours = ec;
					final Socket sock = doorbell;
					if (encours != null) {
						// create convo (initi� par nous)
						new Thread(new Runnable() {

							@Override
							public void run() {
								// sock est l'aceptation de notre socket d'envoi
								if (encours.getChatw() == null) {
									encours.startConv(sock);
									System.out.println("On a lanc� une conversation\n");
								} else {
									encours.reStartConv(sock);
									System.out.println("La conversation était déjà ouverte.\n");
								}
							}

						}).start();

					} else if (stopped != null) {
						final Conversation s = stopped;
						new Thread(new Runnable() {

							@Override
							public void run() {
								// sock est l'aceptation de notre socket d'envoi
								s.reStartConv(sock);
								System.out.println("On a relancé une conversation.\n");
							}

						}).start();

					} else {

						// create convo initi� par l'autre
						new Thread(new Runnable() {

							@Override
							public void run() {

								Contact contact = getMain().getContactList().findIp(host);

								if (contact == null) {
									System.out.println("Pas dans la liste de contacts\n");
								} else {
									Conversation cn = new Conversation(main, contact);
									cn.startConv(sock);
									System.out.println("On a accept� une conv\n");

								}
							}

						}).start();
					}
				}
				System.out.println("Le receiver du messages manager a �t� arr�t� !\n");

			}
		}).start();

		// Thread d'envoi
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (running) {
					if (c != null && m != null)
						sendMessTo(c, m);
				}

				// On arr�te les conversations en cours elsse sont arrêtées lros de la
				// fermeture des chatwindow
				/*
				 * for (Conversation c : ConvList) { c.stopConv(); }
				 */

				// On arrete tous les recepteurs de messages des conv stopp�es
				for (Conversation c : stoppedConvList) { // en arr�tant le mess man on trop toutes les conv
					c.getR().setRunning(false); // On ne peut plus recevoir de messages
				}
				System.out.println("L'envoyeur du messages manager a �t� ar�t� !\n");
			}

		}).start();

	}

	public MainMenu getMain() {
		return main;
	}

	public ArrayList<Conversation> getConvList() {
		return this.ConvList;
	}

	/*
	 * Cette fonction est appel�e par le ContactsManager lorsqu'un utilisateur se
	 * d�connecte on peut donc tout stopper
	 */
	public synchronized void removeConv(Conversation cv) {
		cv.stopConv();
		this.stoppedConvList.remove(cv);
		cv.getR().setRunning(false);
	}

	public synchronized void removeStoppedConv(Conversation cv) {
		this.stoppedConvList.remove(cv);
		cv.getR().setRunning(false);
	}

	public Conversation getConv(Contact c) {
		Conversation res = null;
		for (Conversation cv : this.ConvList) {
			if (cv.getInterlocutor().equals(c)) {
				res = cv;
			}
		}
		return res;
	}

	public Conversation getStoppedConv(Contact c) {
		Conversation res = null;
		for (Conversation cv : this.stoppedConvList) {
			if (cv.getInterlocutor().equals(c)) {
				res = cv;
			}
		}
		return res;
	}

	public void setRunning(boolean b) {
		this.running = b;
	}

	public synchronized void sendMessTo(Conversation c, Message m) {
		if (ConvList.contains(c)) {
			c.getS().send(m);
			System.out.println("Un message a �t� envoy�.\n");
			this.c = null;
			this.m = null;
		} else {
			System.out.println("la conversation n'est pas r�pertori�.\n");
			this.c = null;
			this.m = null;
		}

	}

	public synchronized void signalMess(Conversation c, Message m) {
		this.c = c;
		this.m = m;
		System.out.println("Pr�paration de l'envoi d'un message\n");
	}

	public ArrayList<Conversation> getStoppedConvList() {
		return stoppedConvList;
	}

}