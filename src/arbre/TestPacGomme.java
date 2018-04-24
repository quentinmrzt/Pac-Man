package arbre;

import model.Map;
import model.Monde;
import model.PacMan;
import model.Personnage;

public class TestPacGomme extends Test {
	private int direction;

	public TestPacGomme(Noeud test, int direction) {
		super(test);
		this.direction = direction;
	}
	
	public TestPacGomme(Noeud p, Noeud g, Noeud d, int direction) {
		super(p,g,d);
		this.direction = direction;
	}

	public String toString() {
		return "Test gomme "+Personnage.afficheDirection(direction);
	}

	public boolean test(Monde monde) {
		PacMan pacMan = monde.getPM();
		Map map = monde.getMap();

		int x = pacMan.getPositionX();
		int y = pacMan.getPositionY();

		switch (direction) {
		case Personnage.HAUT:
			y--;
		case Personnage.DROITE:
			x++;
		case Personnage.BAS:
			y++;
		case Personnage.GAUCHE:
			x--;
		}

		if(map.estGomme(x, y)) {
			return true;
		}

		return false;
	}

	public Test clone() {
		Noeud p = null;

		Noeud g = null;
		if(this.aGauche()) {
			g = this.getGauche().clone();
		}

		Noeud d = null;
		if(this.aDroite()) {
			d = this.getDroite().clone();
		}

		return new TestPacGomme(p,g,d,this.direction);		
	}
}
