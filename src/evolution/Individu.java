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

		this.arbre = arbre;
		this.score = 0;
		this.enJeu = true;
	}

	public int getScore() { return score; }
	public boolean estEnJeu() { return enJeu; }
	public Arbre getArbre() { return arbre; }

	public void run() {
		try {
			// On lance le jeu
			while (!this.getMonde().finDePartie()) {
				
				int direction = arbre.getDirection();

				this.getMonde().directionPersonnage(direction, Monde.PACMAN);

				this.getMonde().tourDeJeu();

				Thread.sleep(50);
			}
			
			enJeu = false;
			score = this.getMonde().getScore();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}     