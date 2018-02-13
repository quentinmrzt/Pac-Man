package model;

public class PacMan {
	protected int vie = 3;
	protected int positionX = 1;
	protected int positionY = 1;
	protected int direction = 0;
	protected int prochaineDirection = 0;
	
	final int STATIQUE = 0;
	final int HAUT = 1;
	final int DROITE = 2;
	final int BAS = 3;
	final int GAUCHE = 4;
	
	public PacMan(int x, int y) {
		positionX = x;
		positionY = y;
		direction = 0;
		prochaineDirection = 0;
	}
	
	public void perteVie() {
		vie--;
	}
	
	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
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
		return "PacMan [positionX=" + positionX + ", positionY=" + positionY + "]";
	}

	public int getDirection() {
		return direction;
	}
	
	public int getProchaineDirection() {
		return prochaineDirection;
	}
	
	
}
