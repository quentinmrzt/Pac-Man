package model;

public class Clyde extends Personnage {
	// Clyde feint l'indifférence. De temps en temps, il choisit une direction au hasard (qui peut être celle de Pac-Man).
	public Clyde(Noeud n) {
		super(1,n);
	}
	
	// ABSTRACT
	public void manger() {
		
	}
}
