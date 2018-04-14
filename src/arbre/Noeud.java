package arbre;

public class Noeud {
	private Noeud pere = null;		
	private Noeud gauche = null;
	private Noeud droite = null;	
	
	public Noeud(Noeud parent) {
		this.pere = parent;
	}
	
	// GETTEUR
	public Noeud getPere() {return pere;}
	public Noeud getGauche() {return gauche;}
	public Noeud getDroite() {return droite;}
	
	// SETTEUR
	public void addGauche(Noeud gauche) {this.gauche = gauche;}
	public void addDroite(Noeud droite) {this.droite = droite;}

	public String toString() {
		return "Noeud";
	}
}
