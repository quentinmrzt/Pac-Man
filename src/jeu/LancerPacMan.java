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
		
		model.getPersonnage(Modelisation.PACMAN).addObserver(fenetre);
		model.getPersonnage(Modelisation.BLINKY).addObserver(fenetre);
		model.getPersonnage(Modelisation.PINKY).addObserver(fenetre);
		model.getPersonnage(Modelisation.INKY).addObserver(fenetre);
		model.getPersonnage(Modelisation.CLYDE).addObserver(fenetre);
		
		model.getMap().addObserver(fenetre);
	}
}