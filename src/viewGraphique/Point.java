package viewGraphique;

public class Point {
	private int x, y, taille;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		this.taille = 4;
	}
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public int getTaille() { return this.taille; }
	public int getCentreX() { return this.getX()-(this.taille/2); }
	public int getCentreY() { return this.getY()-(this.taille/2); }
	
	public boolean surPoint(int x, int y) {
		int delta = this.taille;
		
		boolean auDessusX = x >= this.getX();
		boolean auDessusY = y >= this.getY();
		boolean enDessousX = x <= (this.getX()+this.getTaille()+delta);
		boolean enDessousY = y <= (this.getY()+this.getTaille()+delta);
		
		return auDessusX && auDessusY && enDessousX && enDessousY;
	}
}
