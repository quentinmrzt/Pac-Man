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


	// On notifie le mod�le d'une action si le contr�le est bon
	public void control() {

	}
}