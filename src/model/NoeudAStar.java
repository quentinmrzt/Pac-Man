package model;

public class NoeudAStar {
	private Noeud noeud;

	private int coutG;
	private int coutH;
	private int coutF;

	private NoeudAStar parent;

	public NoeudAStar(Noeud n, NoeudAStar nas) {
		noeud = n;
		parent = nas;

		coutG = 0;
		coutH = 0;
		coutF = 0;
	}

	// GETTEUR
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
	public void setCoutG() {
		coutG = Math.abs(parent.getX()-getX()) + Math.abs(parent.getY()-getY());
	}
	public void setCoutH(int arriveeX, int arriveeY) {
		coutH = Math.abs(getX()-arriveeX) + Math.abs(getY()-arriveeY);
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
