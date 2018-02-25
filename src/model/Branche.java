package model;

public class Branche {
	private Noeud n1 = null;
	private Noeud n2 = null;
	private int distance = 0;
	private boolean horizontal;

	public Branche(Noeud n1, Noeud n2) {
		this.n1 = n1;
		this.n2 = n2;

		// les noeuds sont forcement alignés
		if(n1.getX()==n2.getX()) {
			distance = Math.abs(n1.getY()-n2.getY());
			horizontal = false;
		} else {
			distance = Math.abs(n1.getX()-n2.getX());
			horizontal = true;
		}
		
	}


	// GETTEUR
	public int getDistance() {
		return distance;
	}
	public Noeud getN1() {
		return n1;
	}
	public Noeud getN2() {
		return n2;
	}
	public Noeud getApres(Noeud n) {
		// on retourne celui qu'on est pas
		if (n1.equals(n)) {
			return n2;
		} else {
			return n1;
		}
	}

	// TEST
	public boolean estDessus(int x, int y) {
		if(n1.getX()==n2.getX() && x==n1.getX()) {
			// de haut en bas 
			if (y>=n1.getY() && y<=n2.getY()) {
				return true;
			}
		} else {
			// de gauche à droite
			if (x>=n1.getX() && x<=n2.getX()) {
				return true;
			}
		}

		return false;
	}
	public boolean estHorizontal() {
		return horizontal;
	}
	
	// FONCTION
	public String toString() {
		return "Branche [N1=(" + n1 + "), N2=(" + n2 + "), distance=" + distance + "]";
	}
}
