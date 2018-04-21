package controller;

import model.*;

public class Controller {
	private Monde monde;
	
	public Controller(Monde monde) {
		this.monde = monde;
	}
		
	// CLAVIER
	public void toucheHaut() {
		monde.directionPersonnage(Personnage.HAUT, Monde.PACMAN);
	}
	public void toucheDroite() {
		monde.directionPersonnage(Personnage.DROITE, Monde.PACMAN);
	}
	public void toucheBas() {
		monde.directionPersonnage(Personnage.BAS, Monde.PACMAN);
	}
	public void toucheGauche() {
		monde.directionPersonnage(Personnage.GAUCHE, Monde.PACMAN);
	}
	public void toucheT() {
		monde.trouverCheminBlinky();
	}
	public void touchePageUp() {
		monde.tourDeJeu();		
	}
}