package model;

import java.util.Observable;

public class PacMan extends Observable{
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

	public void setProchaineDirection(int prochaine) {
		prochaineDirection = prochaine;
	}


	public void perteVie() {
		vie--;
	}

	public void enHaut() {
		direction = HAUT;
		positionY--;
	}

	public void aDroite() {
		direction = DROITE;
		positionX++;
	}

	public void enBas() {
		direction = BAS;
		positionY++;
	}

	public void aGauche() {
		direction = GAUCHE;
		positionX--;
	}





	public String toString() {
		return "PacMan [vie=" + vie + ", positionX=" + positionX + ", positionY=" + positionY + ", direction="
				+ direction + ", prochaineDirection=" + prochaineDirection + "]";
	}
}
