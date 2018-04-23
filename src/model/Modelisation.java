package model;

import graphe.Graphe;

public abstract class Modelisation implements Runnable {
	private Monde monde;

	public Modelisation(Map map, Graphe graphe) {
		monde = new Monde(map,graphe);
	}
	
	public Modelisation(Monde monde) {
		this.monde = monde;
	}
	
	public Monde getMonde() { return monde; }
	
	public abstract void run();
}