package model;

import java.util.ArrayList;

public abstract class Fantome extends Personnage {
	private Personnage pacMan;
	private ArrayList<NoeudAStar> chemin;
	
	public Fantome(int x, int y, Branche b, Personnage pm) {
		super(1, x, y, b);
		
		pacMan =  pm;
		chemin = new ArrayList<NoeudAStar>();
		
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
			NoeudAStar destination = chemin.get(chemin.size()-1);

			if (destination.getDirection()==HAUT) {
				this.directionHaut();
			} else if (destination.getDirection()==DROITE) {
				this.directionDroite();
			} else if (destination.getDirection()==BAS) {
				this.directionBas();
			} else if (destination.getDirection()==GAUCHE) {
				this.directionGauche();
			} else {
				System.err.println("ERREUR: decision direction.");
			}

			chemin.remove(destination);
		}
	}
	
	public Personnage getPacMan() {
		return pacMan;
	}

	public void setChemin(ArrayList<NoeudAStar> chemin) {
		this.chemin = chemin;
	}


	public abstract void trouverChemin();
}
