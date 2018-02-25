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
	
	// CLAVIER PANNEAU
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
}