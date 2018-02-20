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
	
	// CLAVIER FENETRE
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
	
	// BOUTON PANNEAU
	public void boutonDemarrer() {
		System.out.println("BOUTON 1");
	}
	public void boutonStop() {
		System.out.println("BOUTON 2");
	}
}