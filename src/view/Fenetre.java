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
		this.setSize(800, 600);
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
		addKeyListener(new ControleClavier(this.controler));

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
		// Si c'est Modelisation qui m'a appel�
		if(o instanceof Modelisation){
			
			//System.out.println("Je suis dans la Fenetre et la valeur a �t� modifi�: ");
			
			// On maj le panneau
			panneau.update(o,arg);
		}
	}

	// Implementation de Runnable
	public void run() {
		// Notre horloge 
		while(true) { 	
			// Permet l'orientation au noeud
			controler.destinationPacMan();
			// et on dit � pacMan d'y aller
			controler.deplacementPacMan();

			repaint(); // Fait appel � paint(), maj la fenetre
			
			try {
				Thread.sleep(100); // attente de 100 ms
			} catch(InterruptedException e) { 
				System.err.println("ERREUR: Probl�me sur l'horloge.");
			}
		} 
	}
}