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
		int distance = 1;

		int x = pacMan.getPositionX();
		int y = pacMan.getPositionY();
		for (int i=1 ; i<=distance ; i++) {
			switch (direction) {
			case Personnage.HAUT:
				y -= i;
			case Personnage.DROITE:
				x += i;
			case Personnage.BAS:
				y += i;
			case Personnage.GAUCHE:
				x -= i;
			}

			if(map.estGomme(x, y)) {
				return true;
			}
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

		TestPacGomme courant = new TestPacGomme(p,g,d,this.direction);

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
