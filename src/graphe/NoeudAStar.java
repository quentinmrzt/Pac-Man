package graphe;

import model.Personnage;

public class NoeudAStar {
	private Noeud noeud;

	private int coutG;
	private int coutH;
	private int coutF;

	private NoeudAStar parent;
	private int direction;

	/**
	 * 
	 * @param n Le noeud auquel il réfère
	 * @param nas Le NAS parent
	 * @param d Le sens pour aller à ce noeud, selon le parent
	 */
	public NoeudAStar(Noeud n, NoeudAStar nas, int d) {
		noeud = n;
		parent = nas;

		coutG = 0;
		coutH = 0;
		coutF = 0;
		
		direction = d;
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
	public int getCoutG() {
		return coutG;
	}
	public int getCoutH() {
		return coutH;
	}
	public int getCoutF() {
		return coutF;
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
	}
	/**
	 * Calcule la distance entre le point étudié et le dernier point qu'on a jugé comme bon
	 * @param dernierNoeudBon Dernier point de la liste fermée
	 */
	public void setCoutG(NoeudAStar dernierNoeudBon) {
		coutG = Math.abs(dernierNoeudBon.getX()-this.getX()) + Math.abs(dernierNoeudBon.getY()-this.getY());
	}
	/**
	 * Calcule la distance entre le point étudié et le point de destination
	 * @param noeudArrivee Le noeud destination
	 */
	public void setCoutH(NoeudAStar noeudArrivee) {
		coutH = Math.abs(this.getX()-noeudArrivee.getX()) + Math.abs(this.getY()-noeudArrivee.getY());
	}
	public void setCoutF() {
		coutF = coutG + coutH;
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
