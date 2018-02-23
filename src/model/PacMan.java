package model;

public class PacMan extends Personnage {


	public PacMan() {
		super(14,23,3);
	}

	// ABSTRACT
	// PacMan se deplace dans le tableau selon sa prochaine destination
	// On part du principte que sa destination est horizontal ou vertical à lui
	public void deplacement() {
		// Position de PacMan
		int xPM = this.getPositionX();
		int yPM = this.getPositionY();
		// Position de la destination de PacMan
		int xG = graphe.getPosActuelle().getX();
		int yG = graphe.getPosActuelle().getY();

		if (yPM > yG) {
			// HAUT
			this.enHaut();
		} else if(xPM < xG) {
			// DROITE
			this.aDroite();
		} else if(yPM < yG) {
			// BAS
			this.enBas();
		} else if(xPM > xG) {
			// GAUCHE
			this.aGauche();
		}
	}

	@Override
	public void destination() {
		// Position de PacMan
		int xPM = this.getPositionX();
		int yPM = this.getPositionY();
		// Position de la destination de PacMan
		int xG = graphe.getPosActuelle().getX();
		int yG = graphe.getPosActuelle().getY();
		boolean reorientation = false;

		// si PacMan se trouve sur un noeud
		if (xPM == xG && yPM == yG) {
			// On test s'il doit prendre une nouvelle direction..
			if (this.getProchaineDirection()==HAUT) {
				// HAUT
				if(graphe.deplacementHaut()) {
					reorientation = true;
				}
			} else if(pacMan.getProchaineDirection()==DROITE) {
				// DROITE
				if(graphe.deplacementDroite()) {
					reorientation = true;
				}
			} else if(this.getProchaineDirection()==BAS) {
				// BAS
				if(graphe.deplacementBas()) {
					reorientation = true;
				}
			} else if(this.getProchaineDirection()==GAUCHE) {
				// GAUCHE
				if(graphe.deplacementGauche()) {
					reorientation = true;
				}
			}

			// ..et s'il n'y a pas eu de réorientation, on continue notre chemin dans la même direction
			if (!reorientation) {
				if (this.getDirection()==HAUT) {
					// HAUT
					if(!graphe.deplacementHaut()) {
						this.setProchaineDirection(STATIQUE);
					}
				} else if(this.getDirection()==DROITE) {
					// DROITE
					if(!graphe.deplacementDroite()) {
						this.setProchaineDirection(STATIQUE);
					}
				} else if(this.getDirection()==BAS) {
					// BAS
					if(!graphe.deplacementBas()) {
						this.setProchaineDirection(STATIQUE);
					}
				} else if(this.getDirection()==GAUCHE) {
					// GAUCHE
					if(!graphe.deplacementGauche()) {
						this.setProchaineDirection(STATIQUE);
					}
				}
			}
		}
	}
}
