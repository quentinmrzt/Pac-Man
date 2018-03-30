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
		
		// Avec �a: on a une fenetre avec un menu
		Fenetre fenetre = new Fenetre(control,model);
		
		// La fen�tre devient observeur du model
		model.addObserver(fenetre);
		
		// On d�fini une instance
		Horloge.getInstance();
		
		model.jeu();
	}
}