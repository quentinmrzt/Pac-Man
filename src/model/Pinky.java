package model;

import graphe.Branche;

public class Pinky extends Fantome {
	final int POSITIONPRISONX = 13;
	final int POSITIONPRISONY = 14;
	
	// Pinky a tendance à se mettre en embuscade. Elle vise l'endroit où va se trouver Pac-Man.
	public Pinky(int x, int y, Branche b, Personnage pm) {
		super(x,y,b,pm);
	}

	// ABSTRACT
	public void trouverChemin() {
		// Pinky regarde la ou pacMan sera 

	}
	
	public int getPositionPrisonX() {
		return POSITIONPRISONX;
	}

	public int getPositionPrisonY() {
		return POSITIONPRISONY;
	}
}
