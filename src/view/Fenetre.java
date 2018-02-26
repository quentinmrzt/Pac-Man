package view;

import java.awt.KeyboardFocusManager;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import controller.Controller;
import model.Modelisation;

public class Fenetre extends JFrame implements Observer, Runnable {
	private Modelisation modelisation;
	private Menu menu;
	private Panneau panneau;
	private Thread horloge; 

	public Fenetre(Controller controller, Modelisation model) {
		this.setSize(900, 600);
		this.setTitle("Pac-Man");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		modelisation = model;

		// Ajout d'un écouteur sur le clavier
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new ControleClavier(controller));

		// Panneau
		panneau = new Panneau(modelisation);
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
		panneau.update(o,arg);
	}

	// Implementation de Runnable
	public void run() {
		// Notre horloge 
		while(true) {
			// Permet l'orientation au noeud
			modelisation.destinationPersonnages();
			// on dit à pacMan d'y aller
			modelisation.deplacementPersonnages();
			// et on mange sur notre chemin
			modelisation.manger();

			// Appel tout les paint(): Fenetre, Panneau et ZoneDeJeu
			repaint();

			try {
				Thread.sleep(80); // attente de 100 ms
			} catch(InterruptedException e) { 
				System.err.println("ERREUR: Problème sur l'horloge.");
			}
		} 
	}
}