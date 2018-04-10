package jeu;

import controller.Controller;
import model.Modelisation;
import view.Fenetre;

public class LancerPacMan {
	public static void main(String[] args) {
		// Notre model
		Modelisation model = new Modelisation();

		// Notre controler
		Controller control = new Controller(model);

		// Avec ça: on a une fenetre avec un menu
		Fenetre fenetre = new Fenetre(control,model);

		// La fenêtre devient observeur du model
		model.addObserver(fenetre);

		// On lance le jeu
		while (model.finDePartie()) {
			model.tourDeJeu();

			try {
				Thread.sleep(80);
			} catch(InterruptedException e) { 
				System.err.println("ERREUR: Problème sur l'horloge.");
			}

		}
	}
}