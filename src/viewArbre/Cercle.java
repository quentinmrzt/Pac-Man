package viewArbre;

import arbre.Noeud;

public class Cercle {
	private int x, y, taille;
	private Noeud noeud;
	
	public Cercle(Noeud noeud, int x, int y) {
		this.x = x;
		this.y = y;
		this.noeud = noeud;
		
		this.taille = 60;
	}
	
	public boolean estDansCercle(int x, int y) {
		return (x>=this.x && x<=(this.x+this.taille) && y>=this.y && y<=(this.y+this.taille));
	}
	
	public Noeud getNoeud() { return noeud; }
}
