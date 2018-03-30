package model;

import java.util.ArrayList;

import graphe.AStar;
import graphe.Branche;

public class Clyde extends Fantome {
	final int POSITIONPRISONX = 15;
	final int POSITIONPRISONY = 14;
	final int ENTREEENJEU = 200;

	// Clyde feint l'indiff�rence. De temps en temps, il choisit une direction au hasard (qui peut �tre celle de Pac-Man).
	public Clyde(int x, int y, Branche b, Personnage pm) {
		super(x,y,b,pm);
	}
	
	// ABSTRACT
	public void trouverChemin() {
		//this.setChemin(AStar.trouverChemin(this, this.getPacMan()));
	}
	
	public int getPositionPrisonX() {return POSITIONPRISONX;}
	public int getPositionPrisonY() {return POSITIONPRISONY;}
	public int getEntreeEnJeu() {return ENTREEENJEU;}
}
