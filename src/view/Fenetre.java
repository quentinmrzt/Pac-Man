package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import controller.Controller;

public class Fenetre extends JFrame implements Observer, Runnable {
	private Controller controler;
	private Menu menu;
	private Panneau panneau;
	private Thread horloge; 

	public Fenetre(Controller c) {
		this.setSize(900, 600);
		this.setTitle("Pac-Man");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		this.controler = c;

		// Panneau
		panneau = new Panneau(this.controler);
		this.add(panneau);

		// Menu
		menu = new Menu();
		this.setJMenuBar(menu);

		// Gestion de l'horloge
		horloge = new Thread(this);
		horloge.start();

		//pack();
		this.setVisible(true);
		this.requestFocus();
	}

	// GETTEUR
	public Panneau getPanneau() {
		return panneau;
	}

	// Implementation de Observer
	public void update(Observable o, Object arg) {
		System.out.println("MAJ FENETRE");
		panneau.update(o,arg);
	}

	// Implementation de Runnable
	public void run() {
		// Notre horloge 
		while(true) {
			// Permet l'orientation au noeud
			controler.getModel().destinationPersonnages();
			// on dit à pacMan d'y aller
			controler.getModel().deplacementPersonnages();
			// et on mange sur notre chemin
			// controler.getModel().mangerPacGomme();

			// Appel tout les paint(): Fenetre, Panneau et ZoneDeJeu
			repaint();

			try {
				Thread.sleep(100 ); // attente de 100 ms
			} catch(InterruptedException e) { 
				System.err.println("ERREUR: Problème sur l'horloge.");
			}
		} 
	}
}