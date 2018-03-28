package graphe;

import model.Personnage;

public class NoeudAStar {
	private Noeud noeud;

	private int distDepartCourant=0;
	private int distCourantArrivee=0;
	private int heuristique=0;

	private NoeudAStar parent;
	private int direction; // direction entre Parent -> Courant

	public NoeudAStar(Noeud noeud, NoeudAStar parent, NoeudAStar destination) {
		this.noeud = noeud;
		this.parent = parent;

		if (parent!=null) {
			this.distDepartCourant = Math.abs(this.getX()-parent.getX()) + Math.abs(this.getY()-parent.getY());

			// Calcul de la direction
			if(this.getX()==parent.getX()) {
				// Vertical
				if(parent.getY()<this.getY()) {
					this.direction = Personnage.BAS;
				} else if(this.getY()<parent.getY()) {
					this.direction = Personnage.HAUT;
				} else {
					System.out.println("ERREUR: Le parent et le noeud sont identique: "+this.getX()+"/"+this.getY()+".");
				}
			} else if(this.getY()==parent.getY()) {
				// Horizontal
				if(parent.getX()<this.getX()) {
					this.direction = Personnage.DROITE;
				} else if(this.getX()<parent.getX()) {
					this.direction = Personnage.GAUCHE;
				} else {
					System.out.println("ERREUR: Le parent et le noeud sont identique: "+this.getX()+"/"+this.getY()+".");
				}
			} else {
				System.out.println("ERREUR: Il n'y a aucun axe possible entre parent et courant: "+this.getX()+"/"+this.getY()+".");
			}
		} else {
			// pas de parent, alors pas de direction
			this.distDepartCourant = 0;
			this.direction = Personnage.STATIQUE;
		}

		if (destination!=null) {
			this.distCourantArrivee = Math.abs(this.getX()-destination.getX()) + Math.abs(this.getY()-destination.getY());
		} else {
			this.distCourantArrivee = 0;
		}

		this.heuristique = distDepartCourant+distCourantArrivee;
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
	public void setParent(NoeudAStar nas) {
		parent = nas;

		// Calcul de la direction
		if(this.getX()==parent.getX()) {
			// Vertical
			if(parent.getY()<this.getY()) {
				this.direction = Personnage.BAS;
			} else if(this.getY()<parent.getY()) {
				this.direction = Personnage.HAUT;
			} else {
				System.err.println("ERREUR: Le parent et le noeud sont identique.");
			}
		} else {
			// Horizontal
			if(parent.getX()<this.getX()) {
				this.direction = Personnage.DROITE;
			} else if(this.getX()<parent.getX()) {
				this.direction = Personnage.GAUCHE;
			} else {
				System.err.println("ERREUR: Le parent et le noeud sont identique: "+this.getX()+"/"+this.getY()+".");
			}
		}
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
