package model;

public class Inky extends Personnage {
	// Inky est capricieux. De temps en temps, il part dans la direction opposée à Pac-Man.
	public Inky(Noeud n) {
		super(1,n);	
	}

	// ABSTRACT
	public void manger() {
		
	}
}
