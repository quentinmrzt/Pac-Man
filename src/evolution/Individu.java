package evolution;

import arbre.Arbre;
import graphe.Graphe;
import model.Map;
import model.Modelisation;
import model.Monde;

public class Individu extends Modelisation {
	private int score;
	private boolean enJeu;
	private Arbre arbre;

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
		// On affiche le nom du thread où on se trouve
		System.out.println("Execution dans le thread " + Thread.currentThread().getName());
		
		// On lance le jeu
		while (this.getMonde().finDePartie()) {
			int direction = arbre.getDirection();
			
			this.getMonde().directionPersonnage(direction, Monde.PACMAN);
			
			this.getMonde().tourDeJeu();
			
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		enJeu = !this.getMonde().finDePartie();
		score = this.getMonde().getScore();
		System.out.println("Score avant: "+score);
	}     
}