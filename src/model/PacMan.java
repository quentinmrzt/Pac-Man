package model;

import java.util.Observable;

public class PacMan extends Observable {
	//final int STATIQUE = 0;
	final int HAUT = 0;
	final int DROITE = 1;
	final int BAS = 2;
	final int GAUCHE = 3;
	final int STATIQUE = 4;

	private int vie = 3;
	private int positionX = 1;
	private int positionY = 1;
	private int direction = DROITE;
	private int prochaineDirection = STATIQUE;

	public PacMan(int x, int y) {
		positionX = x;
		positionY = y;
		direction = STATIQUE;
		prochaineDirection = STATIQUE;
	}


	// GETTEUR
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

	// SETTEUR
	public void setProchaineDirection(int prochaine) {
		if (prochaine==HAUT || prochaine==DROITE || prochaine==BAS || prochaine==GAUCHE  || prochaine==STATIQUE ) {
			prochaineDirection = prochaine;
		} else {
			System.err.println("Une direction est ini avec une valeur interdite.");
		}
	}

	public void perteVie() {
		vie--;
	}

	public void enHaut() {
		direction = HAUT;
		positionY--;
		
		setChanged();
		notifyObservers(positionY);
	}

	public void aDroite() {
		direction = DROITE;
		positionX++;
		
		setChanged();
		notifyObservers(positionX);
	}

	public void enBas() {
		direction = BAS;
		positionY++;
		
		setChanged();
		notifyObservers(positionY);
	}

	public void aGauche() {
		direction = GAUCHE;
		positionX--;
		
		setChanged();
		notifyObservers(positionX);
	}
	public void etreStatique() {
		direction = STATIQUE;
	}




	public String toString() {
		return "PacMan [vie=" + vie + ", positionX=" + positionX + ", positionY=" + positionY + ", direction="
				+ direction + ", prochaineDirection=" + prochaineDirection + "]";
	}
}
