package test;

import controller.Controller;
import model.Modelisation;
import view.Fenetre;

public class LancerPacMan {
	public static void main(String[] args) {
		// Notre model
		Modelisation model = new Modelisation();
		
		// Notre controler
		Controller controler = new Controller(model);
		
		// Avec �a: on a une fenetre avec un menu
		Fenetre fenetre = new Fenetre(controler);
		
		// La fen�tre devient observeur du model
		model.addObserver(fenetre);
		model.addObserver(fenetre.getPanneau());
	}
}