package model;

import graphe.Branche;

public class Inky extends Fantome {
	final int POSITIONPRISONX = 14;
	final int POSITIONPRISONY = 14;
	
	// Inky est capricieux. De temps en temps, il part dans la direction oppos�e � Pac-Man.
	public Inky(int x, int y, Branche b, Personnage pm) {
		super(x,y,b,pm);
	}

	// ABSTRACT
	public void trouverChemin() {
		
	}
	
	public int getPositionPrisonX() {
		return POSITIONPRISONX;
	}

	public int getPositionPrisonY() {
		return POSITIONPRISONY;
	}
}
