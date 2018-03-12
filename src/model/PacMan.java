package model;

public class PacMan extends Personnage {
	public PacMan(int x, int y, Branche b) {
		super(3,x,y,b);
		
		this.vulnerable();
		this.enJeu();
	}
}
