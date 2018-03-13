package model;

public class Pinky extends Fantome {
	final int POSITIONPRISONX = 13;
	final int POSITIONPRISONY = 13;
	
	// Pinky a tendance à se mettre en embuscade. Elle vise l'endroit où va se trouver Pac-Man.
	public Pinky(int x, int y, Branche b, Personnage pm) {
		super(x,y,b,pm);
	}

	// ABSTRACT
	public void trouverChemin() {
		// Pinky regarde la ou pacMan sera 
		Noeud noeudFantome = this.getNoeudDestination();
		Noeud noeudPacMan = this.getPacMan().getNoeudDestination();
		
		this.setChemin(AStar.trouverChemin(noeudFantome,noeudPacMan));
	}
}
