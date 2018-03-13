package model;

public class Blinky extends Fantome {
	final int POSITIONPRISONX = 12;
	final int POSITIONPRISONY = 14;
	
	// Blinky attaque directement Pac Man. Il suit Pac-Man comme son ombre.
	public Blinky(int x, int y, Branche b, Personnage pm) {
		super(x,y,b,pm);
		this.enJeu();
	}

	// ABSTRACT
	public void trouverChemin() {
		// Blinky regarde la ou pacMan était 
		Noeud noeudFantome = this.getNoeudDestination();
		Noeud noeudPacMan = this.getPacMan().getNoeudDepart();
		
		this.setChemin(AStar.trouverChemin(noeudFantome,noeudPacMan));
	}

	public int getPositionPrisonX() {
		return POSITIONPRISONX;
	}

	public int getPositionPrisonY() {
		return POSITIONPRISONY;
	}
}
