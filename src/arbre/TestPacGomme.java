package arbre;

import model.Map;
import model.Monde;
import model.PacMan;
import model.Personnage;

public class TestPacGomme extends Test {
	private int direction;

	public TestPacGomme(Noeud test, Monde monde, int direction) {
		super(test,monde);
		this.direction = direction;
	}
	
	public String toString() {
		return "Test gomme "+Personnage.afficheDirection(direction);
	}

	public boolean test() {
		PacMan pacMan = this.getMonde().getPM();
		Map map = this.getMonde().getMap();

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
}
