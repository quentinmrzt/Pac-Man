package arbre;

import model.Monde;
import model.PacMan;
import model.Personnage;

public class TestMur extends Test {
	private int direction;

	public TestMur(Noeud test, Monde monde, int direction) {
		super(test,monde);
		this.direction = direction;
	}
	
	public String toString() {
		return "Test mur "+Personnage.afficheDirection(direction);
	}

	public boolean test() {
		PacMan pacMan = this.getMonde().getPM();
		
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

}
