package controller;

import model.*;

public class Controller {
	private Modelisation model;
	
	final int HAUT=0, DROITE=1, BAS=2, GAUCHE=3;
	final int PACMAN=0, BLINKY=1, PINKY=2, INKY=3, CLYDE=4;
	
	public Controller(Modelisation m) {
		model = m;
	}
	
	// GETTEUR
	public Modelisation getModel() {
		return model;
	}
	
	// CLAVIER PANNEAU
	public void toucheHaut() {
		model.directionPersonnage(HAUT,PACMAN);
	}
	public void toucheDroite() {
		model.directionPersonnage(DROITE,PACMAN);
	}
	public void toucheBas() {
		model.directionPersonnage(BAS,PACMAN);
	}
	public void toucheGauche() {
		model.directionPersonnage(GAUCHE,PACMAN);
	}

}