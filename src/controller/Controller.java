package controller;

import model.*;

public class Controller {
	private Monde monde;
	
	public Controller(Monde monde) {
		this.monde = monde;
	}
	
	// GETTEUR
	public Monde getMonde() {
		return monde;
	}
	
	// CLAVIER PACMAN
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
	
	// CLAVIER BLINKY
	public void toucheHautBlinky() {
		monde.directionPersonnage(Personnage.HAUT, Monde.BLINKY);
	}
	public void toucheDroiteBlinky() {
		monde.directionPersonnage(Personnage.DROITE, Monde.BLINKY);
	}
	public void toucheBasBlinky() {
		monde.directionPersonnage(Personnage.BAS, Monde.BLINKY);
	}
	public void toucheGaucheBlinky() {
		monde.directionPersonnage(Personnage.GAUCHE, Monde.BLINKY);
	}
	
	public void toucheTrouveBlinky() {
		monde.trouverCheminBlinky();
	}

	public void touchePageUp() {
		monde.tourDeJeu();		
	}

	public void toucheP() {
		monde.pause();
	}
	public void toucheJ() {
		monde.jouer();
	}
}