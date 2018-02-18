package view;

import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import controller.Controller;
import model.Modelisation;


public class Fenetre extends JFrame implements Observer, Runnable {
	private Controller controler;
	private Menu menu;
	private Panneau panneau;
	private Thread h; 

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
		h = new Thread(this);
		h.start();

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
			// On maj le panneau
			System.out.println("Je suis dans la Fenetre et la valeur a été modifié: "+arg.toString());
			panneau.update(o,arg);

			// Si le paramètre modifié est un entier
			if (arg instanceof Integer) {
				System.out.println("Je suis un entier en paramètre.");
			}

			// Si le paramètre modifié est une chaine de caractère
			if(arg instanceof String) {
				System.out.println("Je suis une chaine de caractère en paramètre.");
			}
		}
	}

	// Implementation de Runnable
	/* Deux façons de créer un thread: 
	- Création d'un objet qui hérite de la classe Thread 
	- Execution de la primitive new Thread() sur un objet qui implémente l'interface Runnable */ 

	public void run() {
		// Notre horloge 
		while(true) { 	
			System.out.println("Et une seconde de plus.");
			try {
				h.sleep(1000); // attente d'une seconde
			} catch(InterruptedException e) { 
				System.err.println("ERREUR: Problème sur l'horloge.");
			} 
			
			repaint(); // Fait appel à paint(), maj la fenetre
		} 
	}
}