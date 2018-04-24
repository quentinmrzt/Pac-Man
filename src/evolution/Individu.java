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

		clone.score = this.score;
		clone.enJeu = this.enJeu;
		clone.vitesse = this.vitesse;
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

	public static void main(String[] args) {
		Map map = new Map("src/map_gomme.txt");
		Graphe graphe = new Graphe(map);

		// INDIVIDU 1 ------------------------------------------
		Individu individu_1 = new Individu(map,graphe);
		{
			// ARBRE 1 
			Noeud noeud = new TestPacGomme(null,Personnage.BAS);
			Noeud gauche = new TestMur(null,Personnage.GAUCHE);
			Noeud droite = new TestMur(null,Personnage.DROITE);
			noeud.addGauche(gauche);
			noeud.addDroite(droite);
			gauche.addGauche(new Feuille(noeud,Personnage.GAUCHE));
			gauche.addDroite(new Feuille(noeud,Personnage.GAUCHE));
			droite.addGauche(new Feuille(noeud,Personnage.DROITE));
			droite.addDroite(new Feuille(noeud,Personnage.DROITE));
			individu_1.getArbre().setNoeud(noeud);
		}
		individu_1.score = 50;
		// -----------------------------------------------------

		// INDIVIDU 2 ------------------------------------------
		Individu individu_2 = new Individu(map,graphe);
		{
			// ARBRE 1 
			Noeud noeud = new TestMur(null,Personnage.HAUT);
			Noeud gauche = new TestPacGomme(null,Personnage.GAUCHE);
			Noeud droite = new TestPacGomme(null,Personnage.DROITE);
			noeud.addGauche(gauche);
			noeud.addDroite(droite);
			gauche.addGauche(new Feuille(noeud,Personnage.GAUCHE));
			gauche.addDroite(new Feuille(noeud,Personnage.GAUCHE));
			droite.addGauche(new Feuille(noeud,Personnage.DROITE));
			droite.addDroite(new Feuille(noeud,Personnage.DROITE));
			individu_2.getArbre().setNoeud(noeud);
		}
		individu_2.score = 100;
		// -----------------------------------------------------

		System.out.println("Individu_1 score: "+individu_1.getScore());

		Individu test_1 = individu_1.clone();
		Individu test_2 = individu_1;

		System.out.println("[======================================]");
		System.out.println("Test1 score: "+test_1.getScore());
		System.out.println("Test2 score: "+test_2.getScore());
		System.out.println("----------------------------------------");

		test_1.score = 40;

		System.out.println("Test1 score: "+test_1.getScore());
		System.out.println("Test2 score: "+test_2.getScore());
		System.out.println("[======================================]");

		individu_1.getArbre().affiche();
		System.out.println();
		test_1.getArbre().affiche();
		System.out.println();
		test_2.getArbre().affiche();
		System.out.println("----------------------------------------");

		//test_1.getArbre().getNoeud().addDroite(new Feuille(test_1.getArbre().getNoeud(),Personnage.BAS));
		//test_2.getArbre().getNoeud().addDroite(new Feuille(test_2.getArbre().getNoeud(),Personnage.STATIQUE));
		
		test_1.getArbre().getNoeud().addDroite(individu_2.getArbre().getNoeud());

		System.out.println("----------------------------------------");
		individu_1.getArbre().affiche();
		System.out.println();
		test_1.getArbre().affiche();
		System.out.println();
		test_2.getArbre().affiche();
	}
}     