package jeu;

import model.Modelisation;

public class Horloge implements Runnable {
	final int ATTENTE = 80;
	
	private Modelisation modelisation;
	private Thread horloge;
	private int temps;
	
	public Horloge(Modelisation model) {
		// Gestion de l'horloge
		horloge = new Thread(this);
		horloge.start();
		temps = 0;
		
		modelisation = model;
	}
	
	// Implementation de Runnable
	public void run() {
		// Notre horloge 
		while(true) {			
			// On fait un tour de jeu
			modelisation.tourDeJeu();
			
			try {
				temps += ATTENTE;
				Thread.sleep(ATTENTE); // attente de 80 ms
			} catch(InterruptedException e) { 
				System.err.println("ERREUR: Problème sur l'horloge.");
			}
		} 
	}
	
	public int getTemps() {
		return temps;
	}
}
