package model;

import graphe.Branche;

public class PacMan extends Personnage {
	private int vie;

	public PacMan(int x, int y, Branche b) {
		super(x,y,b);
		vie = 3;
		this.enJeu();
	}

	// GETTEUR
	public int getVie() {return vie;}

	// SETTEUR
	public void perteVie() {
		vie--;
		this.reinitialisation();

		if (vie<0) {
			this.mort();
		}
	}

	// ABSTRACT
	void mort() {
		this.horsJeu();
	}
}
