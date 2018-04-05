package model;

import graphe.Branche;

public class Inky extends Fantome {
	final int POSITIONPRISONX = 14;
	final int POSITIONPRISONY = 14;
	final int ENTREEENJEU = 150;

	// Inky est capricieux. De temps en temps, il part dans la direction opposée à Pac-Man.
	public Inky(int x, int y, Branche b, Personnage pm) {
		super(x,y,b,pm);
	}

	// ABSTRACT
	public void trouverChemin() {
		//this.setChemin(null);
	}
	
	public int getPositionPrisonX() {return POSITIONPRISONX;}
	public int getPositionPrisonY() {return POSITIONPRISONY;}
	public int getEntreeEnJeu() {return ENTREEENJEU;}
}
