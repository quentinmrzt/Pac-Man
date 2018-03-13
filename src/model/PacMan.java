package model;

public class PacMan extends Personnage {
	private int vie;

	public PacMan(int x, int y, Branche b) {
		super(x,y,b);

		vie = 3;

		this.vulnerable();
		this.enJeu();
	}

	// GETTEUR
	public int getVie() {
		return vie;
	}

	// SETTEUR
	public void perteVie() {
		vie--;
		this.reinitialisation();
		this.vulnerable();

		if (vie==0) {
			this.mort();
		}
		
		setChanged();
		notifyObservers("PERTEVIE");
	}

	// ABSTRACT
	void mort() {
		System.out.println("FIN DE LA PARTIE");
		this.horsJeu();
	}
}
