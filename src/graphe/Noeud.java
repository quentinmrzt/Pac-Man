package graphe;

import model.Personnage;

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
	public int getX() {return x;}
	public int getY() {return y;}
	
	// GETTEUR NOEUD
	public Noeud enHaut() {
		if (haut!=null) {
			return haut.getN1();
		} else {
			return null;
		}
	}
	
	public Noeud aDroite() {
		if (droite!=null) {
			return droite.getN2();
		} else {
			return null;
		}
	}
	
	public Noeud enBas() {
		if (bas!=null) {
			return bas.getN2();
		} else {
			return null;
		}
	}
	
	public Noeud aGauche() {
		if (gauche!=null) {
			return gauche.getN1();
		} else {
			return null;
		}
	}

	// GETTEUR BRANCHE
	public Branche getHaut() {return haut;}
	public Branche getDroite() {return droite;}
	public Branche getBas() {return bas;}
	public Branche getGauche() {return gauche;}
	
	public Branche getBrancheDirection(int direction) {
		switch (direction) {
		case Personnage.HAUT:
			return haut;
		case Personnage.DROITE:
			return droite;
		case Personnage.BAS:
			return bas;
		case Personnage.GAUCHE:
			return gauche;
		default:
			return null;
		}
	}

	// TEST
	public boolean existeHaut() {return (haut!=null);}
	public boolean existeDroite() {return (droite!=null);}
	public boolean existeBas() {return (bas!=null);}
	public boolean existeGauche() {return (gauche!=null);}
	
	public boolean existeDirection(int direction) {
		switch (direction) {
		case Personnage.HAUT:
			return (this.existeHaut());
		case Personnage.DROITE:
			return (this.existeDroite());
		case Personnage.BAS:
			return (this.existeBas());
		case Personnage.GAUCHE:
			return (this.existeGauche());
		default:
			return false;
		}
	}

	// SETTEUR
	public void setHaut(Branche haut) {this.haut = haut;}	
	public void setBas(Branche bas) {this.bas = bas;}
	public void setDroite(Branche droite) {this.droite = droite;}
	public void setGauche(Branche gauche) {this.gauche = gauche;}

	// FONCTION
	public String toString() {
		String direction = "";
		String distance = "";
		
		if (this.existeHaut()) {
			direction = direction+"Haut ";
			distance = distance+"H:"+this.getHaut().getDistance()+" ";
		}
		if (this.existeDroite()) {
			direction = direction+"Droite ";
			distance = distance+"D:"+this.getDroite().getDistance()+" ";
		}
		if (this.existeBas()) {
			direction = direction+"Bas ";
			distance = distance+"B:"+this.getBas().getDistance()+" ";
		}
		if (this.existeGauche()) {
			direction = direction+"Gauche ";
			distance = distance+"G:"+this.getGauche().getDistance()+" ";
		}

		return "Pos= x: "+x+" y: "+y+". Dir= "+direction+". Dis= "+distance;
	}

	public boolean equals(Noeud n) {
		return (this.getX()==n.getX() && this.getY()==n.getY());
	}
}
