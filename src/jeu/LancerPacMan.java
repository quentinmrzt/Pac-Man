package jeu;

import controller.Controller;
import model.Modelisation;
import view.Fenetre;

public class LancerPacMan {
	public static void main(String[] args) {
		// Notre model
		Modelisation model = new Modelisation();
		
		// Notre controler
		Controller controler = new Controller(model);
		
		// Avec ça: on a une fenetre avec un menu
		Fenetre f = new Fenetre(controler);
		
		// La fenêtre devient observeur du model
		model.addObserver(f);
	}
}