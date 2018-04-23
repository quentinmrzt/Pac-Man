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
		System.out.println("TestPacGomme");
		return this;
	}
}
