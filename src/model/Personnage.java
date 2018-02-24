package model;

import java.util.Observable;

public abstract class Personnage extends Observable {
	final int HAUT = 0;
	final int DROITE = 1;
	final int BAS = 2;
	final int GAUCHE = 3;
	final int STATIQUE = 4;

	private int vie;
	private int positionDepartX;
	private int positionDepartY;
	private int positionX;
	private int positionY;
	private int direction;
	private int prochaineDirection;

	protected Noeud destination;

	public Personnage(int v, Noeud n) {
		positionDepartX = n.getX();
		positionDepartY = n.getY();

		positionX = positionDepartX;
		positionY = positionDepartY;

		vie = v;

		destination = n;

		direction = STATIQUE;
		prochaineDirection = STATIQUE;
	}

	// GETTEUR
	public int getVie() {
		return vie;
	}
	public int getPositionDepartX() {
		return positionDepartX;
	}
	public int getPositionDepartY() {
		return positionDepartY;
	}
	public int getPositionX() {
		return positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public int getDirection() {
		return direction;
	}
	public int getProchaineDirection() {
		return prochaineDirection;
	}
	public String getDirectionStr() {
		if (direction==HAUT) {
			return "Haut";
		} else if(direction==DROITE) {
			return "Droite";
		} else if(direction==BAS) {
			return "Bas";
		} else if(direction==GAUCHE) {
			return "Gauche";
		} else if(direction==STATIQUE) {
			return "Statique";
		} else {
			return null;
		}
	}
	public String getProchaineDirectionStr() {
		if (prochaineDirection==HAUT) {
			return "Haut";
		} else if(prochaineDirection==DROITE) {
			return "Droite";
		} else if(prochaineDirection==BAS) {
			return "Bas";
		} else if(prochaineDirection==GAUCHE) {
			return "Gauche";
		} else if(prochaineDirection==STATIQUE) {
			return "Statique";
		} else {
			return null;
		}
	} 
	public int getPosDesX() {
		return destination.getX();
	}
	public int getPosDesY() {
		return destination.getY();
	}

	// SETTEUR
	public void perteVie() {
		vie--;

		setChanged();
		notifyObservers("VIE");
	}
	public void enHaut() {
		positionY--;

		setChanged();
		notifyObservers("Y");
	}
	public void aDroite() {
		positionX++;

		setChanged();
		notifyObservers("X");
	}
	public void enBas() {
		positionY++;

		setChanged();
		notifyObservers("Y");
	}
	public void aGauche() {
		positionX--;

		setChanged();
		notifyObservers("X");
	}

	// FONCTION

	// Change la direction ou la prochaine direction
	public void directionHaut() {
		if (getDirection()==BAS) {
			direction = HAUT;
			destination = destination.enHaut();
			prochaineDirection = STATIQUE;
		} else {
			prochaineDirection = HAUT;
		}
	}
	public void directionDroite() {
		if (getDirection()==GAUCHE) {
			direction = DROITE;
			destination = destination.aDroite();
			prochaineDirection = STATIQUE;
		} else {
			prochaineDirection = DROITE;
		}
	}
	public void directionBas() {
		if (getDirection()==HAUT) {
			direction = BAS;
			destination = destination.enBas();
			prochaineDirection = STATIQUE;
		} else {
			prochaineDirection = BAS;
		}
	}
	public void directionGauche() {
		if (getDirection()==DROITE) {
			direction = GAUCHE;
			destination = destination.aGauche();
			prochaineDirection = STATIQUE;
		} else {
			prochaineDirection = GAUCHE;
		}
	}

	// Orientation dans les noeuds
	public void destination() {
		// Position du personnage
		int persoX = this.getPositionX();
		int persoY = this.getPositionY();
		
		// Position de la destination
		int destiX = destination.getX();
		int destiY = destination.getY();
		
		boolean reorientation = false;

		// si le perso se trouve à sa destination
		if (persoX == destiX && persoY == destiY) {
			// On test s'il doit prendre une nouvelle direction..
			if (prochaineDirection==HAUT) {
				if (destination.getHaut()!=null) { // si la branche n'est pas vide
					direction = HAUT;
					prochaineDirection = STATIQUE;
					destination = destination.enHaut();
					reorientation = true;
				}
			} else if(prochaineDirection==DROITE) {
				if (destination.getDroite()!=null) { // si la branche n'est pas vide
					direction = DROITE;
					prochaineDirection = STATIQUE;
					destination = destination.aDroite();
					reorientation = true;
				}
			} else if(prochaineDirection==BAS) {
				if (destination.getBas()!=null) { // si la branche n'est pas vide
					direction = BAS;
					prochaineDirection = STATIQUE;
					destination = destination.enBas();
					reorientation = true;
				}
			} else if(prochaineDirection==GAUCHE) {
				if (destination.getGauche()!=null) { // si la branche n'est pas vide
					direction = GAUCHE;
					prochaineDirection = STATIQUE;
					destination = destination.aGauche();
					reorientation = true;
				}
			}

			// ..et s'il n'y a pas eu de réorientation, on continue notre chemin dans la même direction
			if (!reorientation) {
				if (direction==HAUT) {
					if (destination.getHaut()!=null) { // si la branche n'est pas vide
						destination = destination.enHaut();
					} else {
						direction = STATIQUE;
					}
				} else if(direction==DROITE) {
					if (destination.getDroite()!=null) { // si la branche n'est pas vide
						destination = destination.aDroite();
					} else {
						direction = STATIQUE;
					}
				} else if(direction==BAS) {
					if (destination.getBas()!=null) { // si la branche n'est pas vide
						destination = destination.enBas();
					} else {
						direction = STATIQUE;
					}
				} else if(this.getDirection()==GAUCHE) {
					if (destination.getGauche()!=null) { // si la branche n'est pas vide
						destination = destination.aGauche();
					} else {
						direction = STATIQUE;
					}
				}
			}
		}
	}

	// Se deplace selon la direction du personnage
	public void deplacement() {
		if (direction==HAUT) {
			this.enHaut();
		} else if(direction==DROITE) {
			this.aDroite();
		} else if(direction==BAS) {
			this.enBas();
		} else if(direction==GAUCHE) {
			this.aGauche();
		}
	}
	
	public String toString() {		
		return "X: " + positionX + ", Y: " + positionY + ", direction: "+ getDirectionStr() + ", prochaineDirection: " + getProchaineDirectionStr()+".";
	}
	
	// ABSTRACT
	public abstract void manger();
}
