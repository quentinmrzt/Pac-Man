package model;

public class Inky extends Personnage {
	// Inky est capricieux. De temps en temps, il part dans la direction opposée à Pac-Man.
	public Inky(int x, int y, Branche b) {
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
