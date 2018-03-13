package view;

public abstract class ElementDuJeu {
	final static int COTE = 16;
	
	private int positionX ;
	private int positionY ;
	
	public ElementDuJeu(int x, int y) {
		positionX = x;
		positionY = y;
	}

	// GETTEUR
	public int getPositionX() {
		return positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public int getPositionPixelX() {
		return positionX*COTE;
	}
	public int getPositionPixelY() {
		return positionY*COTE;
	}
	
	// SETTEUR
	public void setPositionTabX(int x) {
		positionX = x;
	}
	public void setPositionTabY(int y) {
		positionY = y;
	}
	
	// FONCTION
	public boolean memeEmplacement(int x, int y) {
		if (positionX==x && positionY==y) {
			return true;
		} else {
			return false;
		}
	}
}
