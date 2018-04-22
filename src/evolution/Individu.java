package evolution;

import arbre.Arbre;
import graphe.Graphe;
import model.Map;
import model.Modelisation;
import model.Monde;

public class Individu extends Modelisation {
	private boolean enJeu = true;
	private int score = 0;
	private Arbre arbre = null;

	public Individu(Map map, Graphe graphe) {
		super(map,graphe);

		this.arbre = new Arbre(this.getMonde(), 4);
		this.score = 0;
		this.enJeu = true;
	}

	public Individu(Map map, Graphe graphe, Arbre arbre) {
		super(map,graphe);

		this.arbre = new Arbre(arbre,this.getMonde());
		this.score = 0;
		this.enJeu = true;
	}

	public int getScore() { return score; }
	public boolean estEnJeu() { return enJeu; }
	public Arbre getArbre() { return arbre; }

	public void run() {
		// On lance le jeu
		while (!this.getMonde().finDePartie()) {
			int direction = arbre.getDirection();
			//System.out.println("Direction: "+Personnage.afficheDirection(direction)+" / X: "+this.getMonde().getPM().getPositionX()+" Y: "+this.getMonde().getPM().getPositionY());

			this.getMonde().directionPersonnage(direction, Monde.PACMAN);

			this.getMonde().tourDeJeu();
		}

		enJeu = false;
		score = this.getMonde().getScore();
	}
}     