package model;

public class Clyde extends Fantome {
	final int POSITIONPRISONX = 15;
	final int POSITIONPRISONY = 14;
	
	// Clyde feint l'indifférence. De temps en temps, il choisit une direction au hasard (qui peut être celle de Pac-Man).
	public Clyde(int x, int y, Branche b, Personnage pm) {
		super(x,y,b,pm);
	}
	
	// ABSTRACT
	public void trouverChemin() {
		
	}
	
	public int getPositionPrisonX() {
		return POSITIONPRISONX;
	}

	public int getPositionPrisonY() {
		return POSITIONPRISONY;
	}
}
