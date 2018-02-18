package model;

public class PacMan {
	final int STATIQUE = 0;
	final int HAUT = 1;
	final int DROITE = 2;
	final int BAS = 3;
	final int GAUCHE = 4;
	
	private int vie = 3;
	private int positionX = 1;
	private int positionY = 1;
	private int direction = DROITE;
	private int prochaineDirection = 0;

	public PacMan(int x, int y) {
		positionX = x;
		positionY = y;
		direction = 0;
		prochaineDirection = 0;
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
