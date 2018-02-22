package model;

import java.util.Observable;

public class PacMan extends Observable {
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

	public PacMan(int x, int y) {
		positionDepartX = x;
		positionDepartY = y;
		
		positionX = x;
		positionY = y;
		
		direction = STATIQUE;
		prochaineDirection = STATIQUE;
		
		vie = 3;
	}


	// GETTEUR
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
	public int getVie() {
		return vie;
	}

	// SETTEUR
	public void setProchaineDirection(int prochaine) {
		if (prochaine==HAUT || prochaine==DROITE || prochaine==BAS || prochaine==GAUCHE  || prochaine==STATIQUE ) {
			prochaineDirection = prochaine;
		} else {
			System.err.println("Une direction est initialisé avec une valeur interdite.");
		}
	}

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

	public String toString() {		
		return "X: " + positionX + ", Y: " + positionY + ", direction: "+ getDirectionStr() + ", prochaineDirection: " + getProchaineDirectionStr()+".";
	}
}
