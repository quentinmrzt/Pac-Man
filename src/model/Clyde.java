package model;

public class Clyde extends Personnage {
	// Clyde feint l'indiff�rence. De temps en temps, il choisit une direction au hasard (qui peut �tre celle de Pac-Man).
	public Clyde(Noeud n) {
		super(1,n);
	}
	
	// ABSTRACT
	public void manger() {
		
	}
}
