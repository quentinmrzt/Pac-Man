package model;

public class Blinky extends Fantome {
	final int POSITIONPRISONX = 12;
	final int POSITIONPRISONY = 13;
	
	// Blinky attaque directement Pac Man. Il suit Pac-Man comme son ombre.
	public Blinky(int x, int y, Branche b, Personnage pm) {
		super(x,y,b,pm);	
		
	}

	// ABSTRACT
	public void manger() {

	}
	
	public void trouverChemin() {
		// Blinky regarde la ou pacMan était 
		Noeud noeudFantome = this.getNoeudDestination();
		Noeud noeudPacMan = this.getPacMan().getNoeudDepart();
		
		this.setChemin(AStar.trouverChemin(noeudFantome,noeudPacMan));
	}


}
