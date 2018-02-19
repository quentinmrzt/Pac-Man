package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import controller.Controller;
import model.Modelisation;


public class Fenetre extends JFrame implements Observer, Runnable {
	private Controller controler;
	private Menu menu;
	private Panneau panneau;
	private Thread horloge; 

	public Fenetre(Controller c) {
		this.setSize(800, 800);
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

		// ajoute un écouteur d'événements personnalisé à la fenêtre
		addKeyListener(new TestKeyListener(this.controler));

		// Gestion de l'horloge
		horloge = new Thread(this);
		horloge.start();

		//pack();
		this.setVisible(true);
	}

	// GETTEUR
	public Panneau getPanneau() {
		return panneau;
	}

	// Implementation de Observer
	public void update(Observable o, Object arg) {
		// Si c'est Modelisation qui m'a appelé
		if(o instanceof Modelisation){
			
			System.out.println("Je suis dans la Fenetre et la valeur a été modifié: ");
			
			// On maj le panneau
			panneau.update(o,arg);
		}
	}

	// Implementation de Runnable
	/* Deux façons de créer un thread: 
	- Création d'un objet qui hérite de la classe Thread 
	- Execution de la primitive new Thread() sur un objet qui implémente l'interface Runnable */ 

	public void run() {
		// Notre horloge 
		while(true) { 	
			//System.out.println("Et une seconde de plus.");

			//System.out.println("PM - X:"+controler.getPM().getPositionX()+" Y:"+controler.getPM().getPositionY());

			// Permet l'orientation au noeud
			controler.destinationPacMan();
			// et on dit à pacMan d'y aller
			controler.deplacementPacMan();



			try {
				Thread.sleep(100); // attente de 100 ms
			} catch(InterruptedException e) { 
				System.err.println("ERREUR: Problème sur l'horloge.");
			} 

			repaint(); // Fait appel à paint(), maj la fenetre
		} 
	}
}