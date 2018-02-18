package controller;

import model.*;

public class Controller {
	protected Modelisation model;
	
	public Controller(Modelisation m) {
		model = m;
	}
	
	
	// GETTEUR
	public Map getMap() {
		return model.getMap();
	}
	
	public PacMan getPM() {
		return model.getPM();
	}

	// SETTEUR
	public void toucheHaut() {
		model.deplacementPMHaut();
	}
	public void toucheDroite() {
		model.deplacementPMDroite();
	}
	public void toucheBas() {
		model.deplacementPMBas();
	}
	public void toucheGauche() {
		model.deplacementPMGauche();
	}

	// On notifie le mod�le d'une action si le contr�le est bon
	public void control() {

	}
}