package model;

public class PacMan extends Personnage {
	

	public PacMan(Noeud n) {
		// X: 14 - Y: 23 - Vie: 3
		super(3,n);
	}

	public void manger() {
		/*int x = getPositionX();
		int y = getPositionY();
		int type = map.getCase(x, y);

		if (type==map.GOMME) {
			map.setCase(x, y, map.SOL);
			map.mangerGomme();
			setScoreGomme();
		} else if (type==map.SUPERGOMME) {
			map.setCase(x, y, map.SOL);
			map.mangerSuperGomme();
			setScoreSuperGomme();
		}*/
	}
}
