package model;

import aStar.AStar;
import graphe.Branche;

public class Blinky extends Fantome {
	final int POSITIONPRISONX = 19;
	final int POSITIONPRISONY = 13;
	final int ENTREEENJEU = 50;

	// Blinky attaque directement Pac Man. Il suit Pac-Man comme son ombre.
	public Blinky(int x, int y, Branche b, Personnage pm) {
		super(x,y,b,pm);
	}

	// ABSTRACT
	public void trouverChemin() {
		if(this.getPacMan().getNoeudDepart()!=null) {
			// Blinky regarde la ou pacMan était 
			this.setChemin(AStar.trouverCheminPersonnage(this, this.getPacMan().getNoeudDepart()));
		} else {
			// Si pacMan est statique
			this.setChemin(AStar.trouverCheminPersonnage(this, this.getPacMan()));
		}
	}

	public int getPositionPrisonX() {return POSITIONPRISONX;}
	public int getPositionPrisonY() {return POSITIONPRISONY;}
	public int getEntreeEnJeu() {return ENTREEENJEU;}
}
