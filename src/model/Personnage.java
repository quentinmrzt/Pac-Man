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
	private Graphe graphe;

	public Personnage(int x, int y, int v) {
		positionDepartX = x;
		positionDepartY = y;
		
		positionX = x;
		positionY = y;
		
		vie = v;
		
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

	// SETTEUR
	public void perteVie() {
		vie--;
		
		setChanged();
		notifyObservers("VIE");
	}
	public void enHaut() {
		direction = HAUT;
		positionY--;
		
		setChanged();
		notifyObservers("Y");
	}
	public void aDroite() {
		direction = DROITE;
		positionX++;
		
		setChanged();
		notifyObservers("X");
	}
	public void enBas() {
		direction = BAS;
		positionY++;
		
		setChanged();
		notifyObservers("Y");
	}
	public void aGauche() {
		direction = GAUCHE;
		positionX--;
		
		setChanged();
		notifyObservers("X");
	}
	public void etreStatique() {
		direction = STATIQUE;
	}
	
	
	// FONCTION
	public void setProchaineDirection(int prochaine) {
		if (prochaine==HAUT || prochaine==DROITE || prochaine==BAS || prochaine==GAUCHE  || prochaine==STATIQUE ) {
			prochaineDirection = prochaine;
		} else {
			System.err.println("Une direction est initialisé avec une valeur interdite.");
		}
	}
	
	public boolean deplacementHaut() {
		// Prochaine direction de PacMan
		setProchaineDirection(HAUT);

		// Si pacMan va en bas changement de direction direct (cas de changement de direction dans un couloir)
		if (getDirection() == BAS && graphe.deplacementHaut()) {
			return true;
		} else {
			return false;
		}
	}
	public boolean deplacementDroite() {
		// Prochaine direction de PacMan
		setProchaineDirection(DROITE);

		// Si pacMan va a gauche changement de direction direct 
		if (getDirection() == GAUCHE && graphe.deplacementDroite()) {
			return true;
		} else {
			return false;
		}
	}
	public boolean deplacementBas() {
		// Prochaine direction de PacMan
		setProchaineDirection(BAS);

		// Si pacMan va en bas changement de direction direct 
		if (getDirection() == HAUT && deplacementBas()) {
			return true;
		} else {
			return false;
		}
	}
	public boolean deplacementGauche() {
		// Prochaine direction de PacMan
		setProchaineDirection(GAUCHE);

		// Si pacMan va a droite changement de direction direct
		if (getDirection() == DROITE && graphe.deplacementGauche()) {
			return true;
		} else {
			return false;
		}
	}

	public String toString() {		
		return "X: " + positionX + ", Y: " + positionY + ", direction: "+ getDirectionStr() + ", prochaineDirection: " + getProchaineDirectionStr()+".";
	}
	
	// ABSTRACT
	public abstract void deplacement();
	public abstract void destination();
}
