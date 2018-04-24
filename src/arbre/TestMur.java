package arbre;

import model.Monde;
import model.PacMan;
import model.Personnage;

public class TestMur extends Test {
	private int direction;

	public TestMur(Noeud test, int direction) {
		super(test);
		this.direction = direction;
	}
	
	public TestMur(Noeud p, Noeud g, Noeud d, int direction) {
		super(p,g,d);
		this.direction = direction;
	}

	public String toString() {
		return "Test mur "+Personnage.afficheDirection(direction);
	}

	public boolean test(Monde monde) {
		PacMan pacMan = monde.getPM();

		if (pacMan.getNoeud()!=null) {
			// Pac man est sur un noeud
			if(pacMan.getNoeud().existeDirection(direction)) {
				return false;
			}
		} else {
			// Pac man est sur une branche
			if(pacMan.getBranche().dansLeSens(direction)) {
				return false;
			}
		}

		return true;
	}

	public Test clone() {
		Noeud p = null;
		if(this.aPere()) {
			p = null;
		}

		Noeud g = null;
		if(this.aGauche()) {
			g = this.getGauche().clone();
		}

		Noeud d = null;
		if(this.aDroite()) {
			d = this.getDroite().clone();
		}
		
		TestMur courant = new TestMur(p,g,d,this.direction);
		
		// On rajoute le père à nos fils
		if(this.aGauche()) {
			courant.getGauche().addPere(courant);
		}

		if(this.aDroite()) {
			courant.getDroite().addPere(courant);
		}
		
		return courant;
	}
}
