package evolution;

import arbre.Arbre;
import graphe.Graphe;
import model.Map;
import model.Modelisation;
import model.Monde;

public class Individu extends Modelisation implements Runnable {

	private Map map;
	private Graphe graphe;

	public boolean enJeu = true;
	public int score = 0;
	public int vitesse = 0;
	public int profondeur = 0;
	private int numero;
	
	public Arbre arbre = null;

	public Individu(Individu individu) {
		super(individu.map,individu.graphe);
		
		this.enJeu = individu.enJeu;
		this.score = individu.score;
		this.vitesse = individu.vitesse;
		this.numero = individu.numero;
		this.profondeur = individu.profondeur;
		
		this.arbre = individu.arbre.clone();
	}
	
	public Individu(Map map, Graphe graphe, int PROFONDEUR, int index) {
		super(map,graphe);

		this.map = map;
		this.graphe = graphe;

		this.arbre = new Arbre(this.getMonde(), PROFONDEUR);
		this.score = 0;
		this.enJeu = true;
		this.vitesse = 0;
		this.profondeur = PROFONDEUR;
		
		this.numero = index;
	}

	public Individu(Map map, Graphe graphe, Arbre arbre, int vitesse) {
		super(map,graphe);

		this.map = map;
		this.graphe = graphe;

		this.arbre = new Arbre(arbre,this.getMonde());
		this.score = 0;
		this.enJeu = true;
		this.vitesse = vitesse;
		this.profondeur = arbre.getProfondeur();
	}

	public int getScore() { return score; }
	public boolean estEnJeu() { return enJeu; }
	public Arbre getArbre() { return arbre; }
	public int getVitesse() { return vitesse; }
	public int getNumero() { return this.numero; }

	public Individu clone() {
		Individu clone = new Individu(this);

		clone.score = this.score;
		clone.enJeu = this.enJeu;
		clone.vitesse = this.vitesse;
		clone.numero = this.numero;
		clone.arbre = this.arbre.clone();

		return clone;
	}

	public void run() {
		// On lance le jeu
		while (!this.getMonde().finDePartie()) {
			int direction = arbre.getDirection();
			//System.out.println("Direction: "+Personnage.afficheDirection(direction)+" / X: "+this.getMonde().getPM().getPositionX()+" Y: "+this.getMonde().getPM().getPositionY());

			this.getMonde().directionPersonnage(direction, Monde.PACMAN);

			this.getMonde().tourDeJeu();

			try {
				Thread.sleep(vitesse);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		enJeu = false;
		score = this.getMonde().getScore();
	}
}     