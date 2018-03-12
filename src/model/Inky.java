package model;

public class Inky extends Fantome {
	// Inky est capricieux. De temps en temps, il part dans la direction opposée à Pac-Man.
	public Inky(int x, int y, Branche b, Personnage pm) {
		super(x,y,b,pm);
	}

	// ABSTRACT
	public void trouverChemin() {
		
	}
}
