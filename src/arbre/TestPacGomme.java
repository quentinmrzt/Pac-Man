package arbre;


import model.Map;
import model.Modelisation;
import model.PacMan;
import model.Personnage;

public class TestPacGomme extends Test {
	private int direction;

	public TestPacGomme(Noeud test, Modelisation modelisation, int direction) {
		super(test,modelisation);
		this.direction = direction;
	}

	public boolean test() {
		PacMan pacMan = this.getModelisation().getPM();
		Map map = this.getModelisation().getMap();

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
