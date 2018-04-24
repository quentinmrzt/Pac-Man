package arbre;

import model.Personnage;

public class Feuille extends Noeud {
	private int direction;
	private String str;
	
	public Feuille(Noeud parent, int direction) {
		super(parent);
		this.direction = direction;
	}
	
	public String toString() {
		return "Aller "+Personnage.afficheDirection(direction);
	}
	
	public int getDirection() {return direction;}
	public String getStr() {return str;}

	public Noeud clone() {		
		Noeud p = null;
		
		Feuille courant = new Feuille(p,this.direction);

		// On rajoute le père à nos fils
		/*if(this.aGauche()) {
			courant.getGauche().addPere(courant);
		}

		if(this.aDroite()) {
			courant.getDroite().addPere(courant);
		}*/

		return courant;	
	}
}
