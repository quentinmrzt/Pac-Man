package model;

public class Branche {
	protected Noeud avant = null;
	protected Noeud apres = null;
	protected int distance = 0;
	
	public Branche(Noeud n1, Noeud n2) {
		avant = n1;
		apres = n2;
		distance = distance(n1,n2);
	}
	
	public Branche(Noeud n1, Noeud n2, int d) {
		avant = n1;
		apres = n2;
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
	
	public Noeud getAvant() {
		return avant;
	}
	
	public Noeud getApres() {
		return apres;
	}
}
