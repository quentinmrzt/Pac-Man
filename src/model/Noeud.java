package model;

public class Noeud {
	int x,y;
	protected Branche haut;
	protected Branche bas;
	protected Branche droite;
	protected Branche gauche;
	
	public Noeud(int x, int y) {
		this.x = x;
		this.y = y;
		
		haut = null;
		bas = null;
		droite = null;
		gauche = null;
	}

	// GETTEUR
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	
	public Branche getHaut() {
		return haut;
	}
	public Branche getBas() {
		return bas;
	}
	public Branche getDroite() {
		return droite;
	}
	public Branche getGauche() {
		return gauche;
	}
	
	// SETTEUR
	public void setHaut(Branche haut) {
		this.haut = haut;
	}
	public void setHaut(Noeud n) {
		this.haut = new Branche(this,n);
	}

	public void setBas(Branche bas) {
		this.bas = bas;
	}
	public void setBas(Noeud n) {
		this.bas = new Branche(this,n);
	}
	
	public void setDroite(Branche droite) {
		this.droite = droite;
	}
	public void setDroite(Noeud n) {
		this.droite = new Branche(this,n);
	}
	
	public void setGauche(Branche gauche) {
		this.gauche = gauche;
	}
	public void setGauche(Noeud n) {
		this.gauche = new Branche(this,n);
	}

	public String toString() {
		String direction = "";
		String distance = "";
		if (this.getHaut()!=null) {
			direction = direction+"Haut ";
			distance = distance+"H:"+this.getHaut().getDistance()+" ";
		}
		if (this.getDroite()!=null) {
			direction = direction+"Droite ";
			distance = distance+"D:"+this.getDroite().getDistance()+" ";
		}
		if (this.getBas()!=null) {
			direction = direction+"Bas ";
			distance = distance+"B:"+this.getBas().getDistance()+" ";
		}
		if (this.getGauche()!=null) {
			direction = direction+"Gauche ";
			distance = distance+"G:"+this.getGauche().getDistance()+" ";
		}
		
		return "Pos= x: "+x+" y: "+y+". Dir= "+direction+". Dis= "+distance;
	}
}
