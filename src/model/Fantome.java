package model;

import java.util.ArrayList;

import graphe.Branche;

public abstract class Fantome extends Personnage {
	private Personnage pacMan;
	private ArrayList<Integer> chemin;

	public Fantome(int x, int y, Branche b, Personnage pm) {
		super(x, y, b);

		pacMan =  pm;
		chemin = new ArrayList<Integer>();

		this.invulnerable();
		this.horsJeu();
	}

	public void decisionDirection() {
		if (this.estSurNoeud()) {
			// On se trouve sur un noeud
			this.prochaineDirection();
		} else if(this.getDirection()==Personnage.STATIQUE) {
			// On est statique sur une branche
			this.prochaineDirection();
		}
	}

	public void prochaineDirection() {
		if (chemin.size()!=0) {
			int destination = chemin.get(chemin.size()-1);

			if (destination==HAUT) {
				this.directionHaut();
			} else if (destination==DROITE) {
				this.directionDroite();
			} else if (destination==BAS) {
				this.directionBas();
			} else if (destination==GAUCHE) {
				this.directionGauche();
			} else {
				System.err.println("ERREUR: decision direction.");
			}

			chemin.remove(chemin.size()-1);
		}
	}

	// GETTEUR
	public int getPositionX() {
		if (this.estEnJeu()) {
			return super.getPositionX();
		} else {
			return getPositionPrisonX();
		}
	}
	public int getPositionY() {
		if (this.estEnJeu()) {
			return super.getPositionY();
		} else {
			return getPositionPrisonY();
		}
	}
	public Personnage getPacMan() {
		return pacMan;
	}

	// SETTEUR
	public void setChemin(ArrayList<Integer> chemin) {
		this.chemin = chemin;
	}

	// ABSTRACT
	public void mort() {
		this.reinitialisation();
		this.invulnerable();
		this.horsJeu();

		setChanged();
		notifyObservers("PERTEVIE");
	}


	public abstract void trouverChemin();
	public abstract int getPositionPrisonX();
	public abstract int getPositionPrisonY();
}
