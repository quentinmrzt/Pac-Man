package model;

import java.util.ArrayList;

import observer.Observable;
import observer.Observer;

public class Modelisation implements Observable {
	// ----------------------------------------
	// Donnée du model
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();   

	
	// ----------------------------------------
	// Observable
	public void addObserver(Observer obs) {
		this.listObserver.add(obs);
	}

	public void removeObserver() {
		listObserver = new ArrayList<Observer>();
	}

	public void notifyObserver(String str) {
		// On maj tout les observeur
		for(Observer obs : listObserver) {
			obs.update(str);
		}
	}

	// ----------------------------------------
	// Getteur

	// ----------------------------------------
	// Setteur

}