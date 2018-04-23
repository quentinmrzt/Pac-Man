package evolution;

import arbre.Arbre;
import arbre.Feuille;
import arbre.Noeud;
import arbre.TestMur;
import arbre.TestPacGomme;
import graphe.Graphe;
import model.Map;
import model.Modelisation;
import model.Monde;
import model.Personnage;

public class Individu extends Modelisation implements java.io.Serializable {
	final private int PROFONDEUR = 3;

	private Map map;
	private Graphe graphe;

	public boolean enJeu = true;
	public int score = 0;
	public Arbre arbre = null;
	public int vitesse = 0;

	public Individu(Map map, Graphe graphe) {
		super(map,graphe);

		this.map = map;
		this.graphe = graphe;

		this.arbre = new Arbre(this.getMonde(), PROFONDEUR);
		this.score = 0;
		this.enJeu = true;
		this.vitesse = 0;
	}

	public Individu(Map map, Graphe graphe, int vitesse) {
		super(map,graphe);

		this.map = map;
		this.graphe = graphe;

		this.arbre = new Arbre(this.getMonde(), PROFONDEUR);
		this.score = 0;
		this.enJeu = true;
		this.vitesse = vitesse;
	}

	public Individu(Map map, Graphe graphe, Arbre arbre, int vitesse) {
		super(map,graphe);

		this.map = map;
		this.graphe = graphe;

		this.arbre = new Arbre(arbre,this.getMonde());
		this.score = 0;
		this.enJeu = true;
		this.vitesse = vitesse;
	}

	public Individu(Individu individu) {
		super(individu.getMonde());

		this.arbre = null;
		this.score = individu.getScore();
		this.enJeu = individu.estEnJeu();
		this.vitesse = individu.getVitesse();
	}

	public int getScore() { return score; }
	public boolean estEnJeu() { return enJeu; }
	public Arbre getArbre() { return arbre; }
	public int getVitesse() { return vitesse; }

	public Individu clone() {
		Individu clone = new Individu(map,graphe);

		clone.arbre = this.arbre.clone();
		clone.score = this.score;
		clone.enJeu = this.enJeu;
		clone.vitesse = this.vitesse;

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

	public static void main(String[] args) {
		Map map = new Map("src/map_gomme.txt");
		Graphe graphe = new Graphe(map);

		Individu individu = new Individu(map,graphe);
		
		Noeud noeud = new TestMur(null,Personnage.HAUT);
		noeud.addGauche(new Feuille(noeud,Personnage.GAUCHE));
		noeud.addDroite(new Feuille(noeud,Personnage.DROITE));
		
		individu.getArbre().setNoeud(noeud);
		individu.score = 50;

		System.out.println("Individu score: "+individu.getScore());

		Individu test1 = individu.clone();
		Individu test2 = individu;

		System.out.println("Test1 score: "+test1.getScore());
		System.out.println("Test2 score: "+test2.getScore());
		System.out.println("----------------------------------------");

		test1.score = 40;

		System.out.println("Test1 score: "+test1.getScore());
		System.out.println("Test2 score: "+test2.getScore());
		System.out.println("----------------------------------------");
		
		individu.getArbre().affiche();
		System.out.println();
		test1.getArbre().affiche();
		System.out.println();
		test2.getArbre().affiche();
		System.out.println("----------------------------------------");

		test1.getArbre().getNoeud().addDroite(new Feuille(noeud,Personnage.BAS));
		test2.getArbre().getNoeud().addDroite(new Feuille(noeud,Personnage.STATIQUE));
		
		test1.getArbre().setNoeud(new TestPacGomme(null,Personnage.DROITE));

		System.out.println("----------------------------------------");
		individu.getArbre().affiche();
		System.out.println();
		test1.getArbre().affiche();
		System.out.println();
		test2.getArbre().affiche();
		
	}
}     