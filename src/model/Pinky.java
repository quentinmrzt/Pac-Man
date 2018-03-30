package model;

import java.util.ArrayList;

import graphe.AStar;
import graphe.Branche;

public class Pinky extends Fantome {
	final int POSITIONPRISONX = 13;
	final int POSITIONPRISONY = 14;
	final int ENTREEENJEU = 100;

	
	// Pinky a tendance à se mettre en embuscade. Elle vise l'endroit où va se trouver Pac-Man.
	public Pinky(int x, int y, Branche b, Personnage pm) {
		super(x,y,b,pm);
	}

	// ABSTRACT
	public void trouverChemin() {
		// Pinky regarde la ou pacMan sera 
		//this.setChemin(AStar.trouverChemin(this, this.getPacMan()));
	}
	
	public int getPositionPrisonX() {return POSITIONPRISONX;}
	public int getPositionPrisonY() {return POSITIONPRISONY;}
	public int getEntreeEnJeu() {return ENTREEENJEU;}
}
