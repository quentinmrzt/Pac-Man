package model;

import arbre.Arbre;
import graphe.Branche;

public class PacMan extends Personnage {
	private int vie;
	private Arbre arbre = null;

	public PacMan(int x, int y, Branche b) {
		super(x,y,b);

		vie = 3;

		this.enJeu();
	}

	// GETTEUR
	public int getVie() {return vie;}
	public Arbre getArbre() {return arbre;}

	// SETTEUR
	public void perteVie() {
		vie--;
		this.reinitialisation();

		if (vie<0) {
			this.mort();
		}
		
		setChanged();
		notifyObservers("PERTEVIE");
	}

	// ABSTRACT
	void mort() {
		this.horsJeu();
	}

	public void addAbre(Arbre arbre) {
		this.arbre = arbre;
	}
}
