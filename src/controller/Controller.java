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
		System.out.println("HAUT");
		model.directionPersonnage(HAUT,PACMAN);
	}
	public void toucheDroite() {
		System.out.println("DROITE");
		model.directionPersonnage(DROITE,PACMAN);
	}
	public void toucheBas() {
		System.out.println("BAS");
		model.directionPersonnage(BAS,PACMAN);
	}
	public void toucheGauche() {
		System.out.println("GAUCHE");
		model.directionPersonnage(GAUCHE,PACMAN);
	}

}