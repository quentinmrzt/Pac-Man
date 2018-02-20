package view;

public abstract class ElementDuJeu {
	final static int COTE = 16;
	
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
	
	// SETTEUR
	public void setPositionTabX(int x) {
		positionTabX = x;
		setPositionX(x*COTE);
	}
	public void setPositionTabY(int y) {
		positionTabY = y;
		setPositionY(y*COTE);
	}
	private void setPositionX(int x) {
		positionX = x;
	}
	private void setPositionY(int y) {
		positionY = y;
	}
}
