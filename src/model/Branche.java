package model;

public class Branche {
	private Noeud n1 = null;
	private Noeud n2 = null;
	private int distance = 0;

	public Branche(Noeud n1, Noeud n2) {
		this.n1 = n1;
		this.n2 = n2;
		distance = distance(n1,n2);
	}

	public Branche(Noeud n1, Noeud n2, int d) {
		this.n1 = n1;
		this.n2 = n2;
		distance = d;
	}

	public int distance(Noeud n1, Noeud n2) {
		// les noeuds sont forcement alignés
		if(n1.getX()==n2.getX()) {
			return Math.abs(n1.getY()-n2.getY());
		} else {
			return Math.abs(n1.getX()-n2.getX());
		}
	}

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
}
