package model;

public abstract class Fantome {
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
	
	public Fantome(int x, int y) {
		positionDepartX = x;
		positionDepartY = y;
		positionX = x;
		positionY = y;
		vie = 1;
		direction = STATIQUE;
		prochaineDirection = STATIQUE;
	}

	public int getVie() {
		return vie;
	}

	public void setVie(int vie) {
		this.vie = vie;
	}

	public int getPositionDepartX() {
		return positionDepartX;
	}

	public void setPositionDepartX(int positionDepartX) {
		this.positionDepartX = positionDepartX;
	}

	public int getPositionDepartY() {
		return positionDepartY;
	}

	public void setPositionDepartY(int positionDepartY) {
		this.positionDepartY = positionDepartY;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getProchaineDirection() {
		return prochaineDirection;
	}

	public void setProchaineDirection(int prochaineDirection) {
		this.prochaineDirection = prochaineDirection;
	}
	
	
}
