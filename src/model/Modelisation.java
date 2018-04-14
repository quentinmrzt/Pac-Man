package model;

import java.util.ArrayList;
import java.util.Observable;

import arbre.Arbre;
import graphe.Branche;
import graphe.Graphe;

public class Modelisation extends Observable {
	// Donn�e du model
	public final static int BLINKY=0;
	public final static int PINKY=1;
	public final static int INKY=2;
	public final static int CLYDE=3; 
	public final static int PACMAN=4;

	public final int SCORE_GOMME=10;
	public final int SCORE_SUPERGOMME=50;
	public final int SCORE_FANTOME=200;

	private Map map;
	private PacMan pacMan;
	private ArrayList<Fantome> fantomes;
	private Graphe graphe;
	private int score;
	private int nombreDeTour;
	private boolean jouer;

	public Modelisation() {
		super();

		map = new Map("src/map_gomme.txt");
		graphe = new Graphe(map);

		// PACMAN
		int pacManX = 14;
		int pacManY = 23;
		Branche branchePacMan = graphe.getBranche(pacManX,pacManY);
		// Ini du personnage PacMan
		pacMan = new PacMan(pacManX,pacManY,branchePacMan);

		// FANTOMES
		int fantomeX = 14;
		int fantomeY = 11;
		Branche brancheFantome = graphe.getBranche(fantomeX, fantomeY);
		// Ini des personnages Fantomes
		fantomes = new ArrayList<Fantome>();
		fantomes.add(new Blinky(fantomeX,fantomeY,brancheFantome,pacMan));
		fantomes.add(new Pinky(fantomeX,fantomeY,brancheFantome,pacMan));
		fantomes.add(new Inky(fantomeX,fantomeY,brancheFantome,pacMan));
		fantomes.add(new Clyde(fantomeX,fantomeY,brancheFantome,pacMan));

		score = 0;
		nombreDeTour = 0;
		jouer = false;
	}

	// ----------------------------------------
	// Getteur
	public Map getMap() {return map;}
	public Graphe getGraphe() {return graphe;}
	public int getScore() {return score;}
	public PacMan getPM() {return pacMan;}
	public int getNombreDeTour() {return nombreDeTour;}
	public Fantome getFantome(int personnage) {return fantomes.get(personnage);}
	public ArrayList<Fantome> getFantome() {return fantomes;}
	public boolean enPause() {return !jouer;}

	public boolean finDePartie() {
		if(!pacMan.estEnJeu() || map.getNbGomme()+map.getNbSuperGomme()==0) {
			System.out.println("FIN DE LA PARTIE");
			return false;
		} else {
			return true;
		}
	}

	public void tourDeJeu() {
		
			pacMan.destination();

			for (Fantome fantome: fantomes) {
				// finEffetSuperGomme
				if(fantome.estVulnerable() && nombreDeTour>fantome.getTourVulnerabilite()+60) {
					fantome.invulnerable();
				}

				// On lib�re un fantome
				if (!fantome.estEnJeu() && nombreDeTour>fantome.getEntreeEnJeu()+fantome.getDatePrison()) {
					fantome.enJeu();
				}

				// 3 - Recherche du chemin et direction
				fantome.trouverChemin();
				fantome.decisionDirection();

				fantome.destination();

				fantome.deplacement();
			}
			this.mangerFantome();
			this.mangerPacMan();

			// PAC-MAN: Il se d�place et mange
			pacMan.deplacement();
			this.mangerFantome();
			this.mangerPacMan();
			this.mangerGomme();

			this.nombreDeTour++;

			// PROVISOIRE
			setChanged();
			notifyObservers("TEST_TOURDEJEU");
	}

	// --------------------------------------
	// MANGER
	public void mangerGomme() {
		int x = pacMan.getPositionX();
		int y = pacMan.getPositionY();
		int type = map.getCase(x, y);

		if (type==Map.GOMME) {
			map.mangerGomme(x,y);
			score+=SCORE_GOMME;
		} else if (type==Map.SUPERGOMME) {
			map.mangerSuperGomme(x,y);
			score+=SCORE_SUPERGOMME;

			for (Fantome fantome: fantomes) {
				if(fantome.estEnJeu()) {
					fantome.vulnerable(nombreDeTour);
				}
			}
		}
	}

	public void mangerPacMan() {
		int x = pacMan.getPositionX();
		int y = pacMan.getPositionY();
		boolean perte = false;	

		for (Fantome fantome: fantomes) {
			// On regarde si le fantome est invuln�rable pour manger PacMan
			if (fantome.estInvulnerable() && fantome.estEnJeu()) {
				if (x==fantome.getPositionX() && y==fantome.getPositionY()) {
					perte = true;
				}
			}
		}

		if (perte) {
			pacMan.perteVie();

			for (Fantome fantome: fantomes) {
				fantome.victoire(nombreDeTour);
			}
		}
	}

	public void mangerFantome() {
		int x = pacMan.getPositionX();
		int y = pacMan.getPositionY();

		for (Fantome fantome: fantomes) {
			// On regarde si le fantome est vuln�rable
			if (!fantome.estInvulnerable() && fantome.estEnJeu()) {
				if (x==fantome.getPositionX() && y==fantome.getPositionY()) {
					fantome.mort();
					//this.mangerDeSuite++;
					this.score += SCORE_FANTOME;
				}
			}
		}
	}

	// ----------------------------------------
	// GESTION DU CLAVIER
	public void directionPersonnage(int direction, int personnage) {
		if (personnage==PACMAN) {
			pacMan.direction(direction);
		} else {
			fantomes.get(personnage).direction(direction);
		}
	}

	public void trouverCheminBlinky() {
		fantomes.get(BLINKY).trouverChemin();
	}

	// Si des sous �lements du model son maj
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers("Modelisation");
	}

	public void pause() {
		this.jouer = false;
	}

	public void jouer() {
		this.jouer = true;
	}
	

	public static void main(String[] args) {
		int profondeur = 10;
		Modelisation modelisation = new Modelisation();
		Arbre arbre = new Arbre(modelisation, profondeur);

		System.out.println("Hauteur: "+arbre.hauteur());
		System.out.println("Nombre de noeud: "+arbre.nbNoeud());
		
		arbre.affiche(arbre.getNoeud(), 0);
	}
}