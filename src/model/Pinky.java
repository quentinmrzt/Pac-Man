package model;

public class Pinky extends Personnage {
	// Pinky a tendance � se mettre en embuscade. Elle vise l'endroit o� va se trouver Pac-Man.
	public Pinky(int x, int y, Branche b) {
		super(1,x,y,b);
	}

	// ABSTRACT
	public void manger() {
		
	}
}
