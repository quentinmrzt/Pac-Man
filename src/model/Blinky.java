package model;

public class Blinky extends Personnage {
	// Blinky attaque directement Pac Man. Il suit Pac-Man comme son ombre.
	public Blinky(int x, int y, Branche b) {
		super(1,x,y,b);	
	}

	// ABSTRACT
	public void manger() {
		
	}
}
