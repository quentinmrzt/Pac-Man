package jeu;

import graphe.Graphe;
import model.Map;
import model.Modelisation;

public class Jeu extends Modelisation {
	public Jeu(Map map, Graphe graphe) {
		super(map, graphe);
	}
	
	public void run() {
		// On lance le jeu
		while (!this.getMonde().finDePartie()) {
			this.getMonde().tourDeJeu();

			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}     
}