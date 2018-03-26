package graphe;

import model.Personnage;

public class NoeudAStar {
	private Noeud noeud;

	private int distDepartCourant=0;
	private int distCourantArrivee=0;
	private int heuristique=0;

	private NoeudAStar parent;
	private int direction;

	// Pour les noeuds départs
	public NoeudAStar(Noeud noeud) {
		this.noeud = noeud;
		this.parent = null;
		this.direction = Personnage.STATIQUE;
		this.distDepartCourant = 0;
		this.distCourantArrivee = 0;
		this.heuristique = 0;
	}

	public NoeudAStar(Noeud noeud, NoeudAStar parent, NoeudAStar destination, int direction) {
		this.noeud = noeud;
		this.parent = parent;
		this.direction = direction;

		if (parent!=null) {
			distDepartCourant = Math.abs(this.getX()-parent.getX()) + Math.abs(this.getY()-parent.getY());
		}

		if (destination!=null) {
			distCourantArrivee = Math.abs(this.getX()-destination.getX()) + Math.abs(this.getY()-destination.getY());
		}

		heuristique = distDepartCourant+distCourantArrivee;
	}

	// GETTEUR
	public NoeudAStar getParent() {
		return parent;
	}
	public Noeud getNoeud() {
		return noeud;
	}
	public int getX() {
		return noeud.getX();
	}
	public int getY() {
		return noeud.getY();
	}
	public int getHeuristique() {
		return heuristique;
	}
	public int getDirection() {
		return direction;
	}
	public String getDirectionStr() {
		return Personnage.afficheDirection(direction);
	}
	public Noeud enHaut() {
		return noeud.enHaut();
	}
	public Noeud aDroite() {
		return noeud.aDroite();
	}
	public Noeud enBas() {
		return noeud.enBas();
	}
	public Noeud aGauche() {
		return noeud.aGauche();
	}

	// SETTEUR
	public void setParent(NoeudAStar nas, int d) {
		parent = nas;
		direction = d;
	}

	// TEST
	public boolean existeHaut() {
		return noeud.existeHaut();
	}
	public boolean existeDroite() {
		return noeud.existeDroite();
	}
	public boolean existeBas() {
		return noeud.existeBas();
	}
	public boolean existeGauche() {
		return noeud.existeGauche();
	}

	// FONCTION
	public boolean equals(Object obj) {
		if (obj==null) {
			System.err.println("ERREUR: Objet vide.");
			return false;
		} else {
			NoeudAStar nas = (NoeudAStar) obj;

			if(this.getX()==nas.getX() && this.getY()==nas.getY()) {
				return true;
			}

			return false;
		}
	}


}
