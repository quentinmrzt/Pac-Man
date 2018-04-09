package graphe;

import model.Personnage;

public class Branche {
	private Noeud n1 = null;
	private Noeud n2 = null;
	private int distance = 0;
	private boolean horizontal;

	public Branche(Noeud n1, Noeud n2) {
		// les noeuds sont forcement alignés
		if(n1.getX()!=n2.getX() && n1.getY()!=n2.getY()) {
			System.err.println("ERREUR: Création d'une branche avec des noeuds non allignés");
		} else {
			this.n1 = n1;
			this.n2 = n2;
			this.distance = Math.abs(n1.getX()-n2.getX() + n1.getY()-n2.getY());
			this.horizontal = (n1.getY()==n2.getY());
		}
	}

	// GETTEUR
	public int getDistance() {return distance;}
	public Noeud getN1() {return n1;}
	public Noeud getN2() {return n2;}

	public Noeud getNoeud(int x, int y) {
		if(n1.getX()==x && n1.getY()==y) {
			return n1;
		} else if(n2.getX()==x && n2.getY()==y) {
			return n2;
		} else {
			return null;
		}
	}

	// TEST
	public boolean estHorizontal() {return horizontal;}
	public boolean estDessus(int x, int y) {return (x>=n1.getX() && x<=n2.getX() && y>=n1.getY() && y<=n2.getY());}

	// FONCTION
	public String toString() {
		return "Branche [N1=(" + n1 + "), N2=(" + n2 + "), distance=" + distance + "]";
	}

	public boolean dansLeSens(int direction) {
		if(horizontal && (direction==Personnage.GAUCHE || direction==Personnage.DROITE)) {
			return true;
		}

		if(!horizontal && (direction==Personnage.HAUT || direction==Personnage.BAS)) {
			return true;
		}

		return false;
	}

	public boolean equals(Branche b) {
		if(b==null) {
			return false;
		}

		return (this.getN1().equals(b.getN1()) && this.getN2().equals(b.getN2()));
	}
}
