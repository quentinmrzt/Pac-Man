package model;

public class PacMan extends Personnage {
	

	public PacMan(int x, int y, Branche b) {
		// X: 14 - Y: 23 - Vie: 3
		super(3,x,y,b);
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
