package model;

public class PacMan {
	protected int vie = 3;
	protected int positionX = 1;
	protected int positionY = 1;
	
	public PacMan() {
		
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
		positionY--;
	}
	
	public void enBas() {
		positionY++;
	}
	
	public void aDroite() {
		positionX++;
	}
	
	public void aGauche() {
		positionX--;
	}
}
