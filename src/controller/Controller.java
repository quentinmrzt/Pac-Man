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

	// SETTEUR


	// On notifie le modèle d'une action si le contrôle est bon
	public void control() {

	}
}