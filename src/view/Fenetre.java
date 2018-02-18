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

		// ajoute un �couteur d'�v�nements personnalis� � la fen�tre
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
		// Si c'est Modelisation qui m'a appel�
		if(o instanceof Modelisation){
			// On maj le panneau
			System.out.println("Je suis dans la Fenetre et la valeur a �t� modifi�: "+arg.toString());
			panneau.update(o,arg);

			// Si le param�tre modifi� est un entier
			if (arg instanceof Integer) {
				System.out.println("Je suis un entier en param�tre.");
			}

			// Si le param�tre modifi� est une chaine de caract�re
			if(arg instanceof String) {
				System.out.println("Je suis une chaine de caract�re en param�tre.");
			}
		}
	}

	// Implementation de Runnable
	/* Deux fa�ons de cr�er un thread: 
	- Cr�ation d'un objet qui h�rite de la classe Thread 
	- Execution de la primitive new Thread() sur un objet qui impl�mente l'interface Runnable */ 

	public void run() {
		// Notre horloge 
		while(true) { 	
			System.out.println("Et une seconde de plus.");
			try {
				h.sleep(1000); // attente d'une seconde
			} catch(InterruptedException e) { 
				System.err.println("ERREUR: Probl�me sur l'horloge.");
			} 
			
			repaint(); // Fait appel � paint(), maj la fenetre
		} 
	}
}