package controller;

import model.*;

public class Controller {
	private Modelisation model;
	
	public Controller(Modelisation m) {
		model = m;
	}
	
	// GETTEUR
	public Modelisation getModel() {
		return model;
	}
	
	// CLAVIER PACMAN
	public void toucheHaut() {
		model.directionPersonnage(Personnage.HAUT, Modelisation.PACMAN);
	}
	public void toucheDroite() {
		model.directionPersonnage(Personnage.DROITE, Modelisation.PACMAN);
	}
	public void toucheBas() {
		model.directionPersonnage(Personnage.BAS, Modelisation.PACMAN);
	}
	public void toucheGauche() {
		model.directionPersonnage(Personnage.GAUCHE, Modelisation.PACMAN);
	}
	
	// CLAVIER BLINKY
	public void toucheHautBlinky() {
		model.directionPersonnage(Personnage.HAUT, Modelisation.BLINKY);
	}
	public void toucheDroiteBlinky() {
		model.directionPersonnage(Personnage.DROITE, Modelisation.BLINKY);
	}
	public void toucheBasBlinky() {
		model.directionPersonnage(Personnage.BAS, Modelisation.BLINKY);
	}
	public void toucheGaucheBlinky() {
		model.directionPersonnage(Personnage.GAUCHE, Modelisation.BLINKY);
	}
	public void toucheTrouveBlinky() {
		model.trouverCheminBlinky();
	}
}