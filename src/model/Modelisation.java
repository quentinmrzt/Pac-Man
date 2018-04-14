package model;

import arbre.Arbre;
import graphe.Graphe;

public class Modelisation extends Thread {
	private Monde monde;
	private int score;
	private boolean enJeu;

	public Modelisation(Map map, Graphe graphe) {
		super();
		monde = new Monde(map,graphe);
		score = 0;

		this.enJeu = true;
		this.start();
	}
	
	public int getScore() { return score; }
	public boolean estEnJeu() { return enJeu; }
	public Arbre getArbre() { return monde.getArbre(); }

	public void run() {
		// On lance le jeu
		while (monde.finDePartie()) {
			monde.tourDeJeu();

			/*try {
				Modelisation.sleep(80);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}
		
		enJeu = false;
		score = monde.getScore();
	}     
}