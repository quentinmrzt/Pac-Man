package arbre;

public abstract class Noeud {
	private Noeud pere = null;		
	private Noeud gauche = null;
	private Noeud droite = null;	

	public Noeud() {
		this.pere = null;		
		this.gauche = null;
		this.droite = null;	
	}
	
	public Noeud(Noeud parent) {
		this.pere = parent;
		this.gauche = null;
		this.droite = null;	
	}

	public Noeud(Noeud parent, Noeud gauche, Noeud droite) {
		this.pere = parent;
		this.gauche = gauche;
		this.droite = droite;
	}

	public abstract Noeud clone();
	
	// GETTEUR
	public Noeud getPere() {return pere;}
	public Noeud getGauche() {return gauche;}
	public Noeud getDroite() {return droite;}
	public boolean aPere() { return pere!=null; }
	public boolean aGauche() { return gauche!=null; }
	public boolean aDroite() { return droite!=null; }

	// SETTEUR
	public void addGauche(Noeud gauche) {this.gauche = gauche;}
	public void addDroite(Noeud droite) {this.droite = droite;}
	public void addPere(Noeud pere) { this.pere = pere; }

	public String toString() {
		return "Noeud";
	}

	public void affiche() {
		this.affiche(this,0);
	}

	private void affiche(Noeud a, int profondeur) {
		if (a == null) {
			return;
		}

		for (int i = 0; i <= profondeur; i++) {
			System.out.print("|");
		}

		System.out.println(a.toString());
		affiche(a.getGauche(), profondeur+1);
		affiche(a.getDroite(), profondeur+1);
	}

	public void inverseNoeud(Noeud noeud) {
		pere.changeFils(this, noeud);
	}

	public boolean changeFils(Noeud fils, Noeud noeud) {
		if (gauche.equals(fils)) {
			//noeud.addPere(this);
			gauche = noeud;
			return true;
		} else if (droite.equals(fils)) {
			//noeud.addPere(this);
			droite = noeud;
			return true;
		} else {
			System.err.println("Tu n'es pas mon fils.");
			return false;
		}
	}
}
