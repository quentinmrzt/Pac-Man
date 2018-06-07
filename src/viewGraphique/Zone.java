package viewGraphique;

public class Zone {
	private Point origine;
	private int hauteur, largeur;
	
	/**
	 * Zone rectangulaire
	 * @param x Point d'origine en X
	 * @param y Point d'origine en Y
	 * @param hauteur Hauteur du rectangle
	 * @param largeur Largeur du rectangle
	 */
	public Zone(int x, int y, int hauteur, int largeur) {
		this.origine = new Point(x,y);
		
		this.hauteur = hauteur;
		this.largeur = largeur;
	}
	
	public int getOrigineX() { return this.origine.getX(); }
	public int getOrigineY() { return this.origine.getY(); }
	
	public int getExtremiteX() { return this.origine.getX()+this.largeur; }
	public int getExtremiteY() { return this.origine.getY()+this.hauteur; }
	
	public int getHauteur() { return this.hauteur; }
	public int getLargeur() { return this.largeur; }
	
	public boolean estDedans(Point point) {
		boolean auDessusX = point.getX() >= this.getOrigineX();
		boolean auDessusY = point.getY() >= this.getOrigineY();
		boolean enDessousX = point.getX() <= (this.getOrigineX()+this.getLargeur());
		boolean enDessousY = point.getY() <= (this.getOrigineY()+this.getHauteur());
		
		return auDessusX && auDessusY && enDessousX && enDessousY;
	}
}
