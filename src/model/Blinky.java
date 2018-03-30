package model;

import java.util.ArrayList;

import graphe.AStar;
import graphe.Branche;

public class Blinky extends Fantome {
	final int POSITIONPRISONX = 12;
	final int POSITIONPRISONY = 14;
	final int ENTREEENJEU = 50;

	// Blinky attaque directement Pac Man. Il suit Pac-Man comme son ombre.
	public Blinky(int x, int y, Branche b, Personnage pm) {
		super(x,y,b,pm);
	}

	// ABSTRACT
	public void trouverChemin() {
		// Blinky regarde la ou pacMan était 
		
		ArrayList<Integer> chemin = AStar.trouverChemin(this, this.getPacMan());
		
		System.out.println("Chemin: ");
		for (int d:chemin) {
			if (d==HAUT) {
				System.out.print("Haut ");
			} else if(d==DROITE) {
				System.out.print("Droite ");
			} else if(d==BAS) {
				System.out.print("Bas ");
			} else if(d==GAUCHE) {
				System.out.print("Gauche ");
			} else if(d==STATIQUE) {
				System.out.print("Statique ");
			}
		}
		System.out.println("");

		this.setChemin(chemin);
	}

	public int getPositionPrisonX() {return POSITIONPRISONX;}
	public int getPositionPrisonY() {return POSITIONPRISONY;}
	public int getEntreeEnJeu() {return ENTREEENJEU;}
}
