package model;

public class Clyde extends Fantome {
	// Clyde feint l'indiff�rence. De temps en temps, il choisit une direction au hasard (qui peut �tre celle de Pac-Man).
	public Clyde(int x, int y, Branche b, Personnage pm) {
		super(x,y,b,pm);
	}
	
	// ABSTRACT
	public void trouverChemin() {
		
	}
}
