package model;

public class Clyde extends Personnage {
	// Clyde feint l'indiff�rence. De temps en temps, il choisit une direction au hasard (qui peut �tre celle de Pac-Man).
	public Clyde(int x, int y, Branche b) {
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
