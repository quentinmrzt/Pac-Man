package view;

public abstract class ElementDuJeu {
	static int COTE = 16;
	
	private int positionTabX ;
	private int positionTabY ;
	private int positionX ;
	private int positionY ;
	
	public ElementDuJeu(int x, int y) {
		positionTabX = x;
		positionTabY = y;
		positionX = x*COTE;
		positionY = y*COTE;
	}

	// GETTEUR
	public int getPositionTabX() {
		return positionTabX;
	}
	public int getPositionTabY() {
		return positionTabY;
	}
	public int getPositionX() {
		return positionX;
	}
	public int getPositionY() {
		return positionY;
	}
}
