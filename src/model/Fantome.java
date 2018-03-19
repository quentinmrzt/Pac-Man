package model;

import java.util.ArrayList;

import graphe.Branche;
import graphe.NoeudAStar;

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

	

	public int compare2Noeuds(NoeudAStar n1, NoeudAStar n2) {
		if (n1.getCoutH()<n2.getCoutH()) {
			return 1;
		} else if (n1.getCoutH()==n2.getCoutH()) {
			return 0;
		} else {
			return -1;
		}
	}

	public void decisionDirection() {
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

			chemin.remove(destination);
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
