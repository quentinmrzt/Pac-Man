package model;

public class Pinky extends Personnage {
	// Pinky a tendance à se mettre en embuscade. Elle vise l'endroit où va se trouver Pac-Man.
	public Pinky(int x, int y, Branche b) {
		super(1,x,y,b);
		this.invulnerable();
	}

	// ABSTRACT
	public void manger() {
		
	}

	public void decisionDirection() {
		
	}

	public void trouverChemin() {
		
	}
}
