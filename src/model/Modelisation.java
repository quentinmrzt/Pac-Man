package model;

import java.util.ArrayList;
import java.util.Observable;

import graphe.Branche;
import graphe.Graphe;
import jeu.Horloge;

public class Modelisation extends Observable {
	// Donnée du model
	public final static int BLINKY=0;
	public final static int PINKY=1;
	public final static int INKY=2;
	public final static int CLYDE=3; 
	public final static int PACMAN=4;

	public final static int SCORE_GOMME=10;
	public final static int SCORE_SUPERGOMME=50;
	public final static int SCORE_FANTOME=200;

	private Map map;
	private PacMan pacMan;
	private ArrayList<Fantome> fantomes;
	private Graphe graphe;
	private int score;
	private int mangerDeSuite;
	private int nombreDeTour;

	public Modelisation() {
		super();

		map = new Map("src/map_gomme.txt");
		graphe = new Graphe(map);


		// PACMAN
		int pacManX = map.getSpawnPacManX();
		int pacManY = map.getSpawnPacManY();
		Branche branchePacMan = graphe.getBranche(pacManX, pacManY);
		// Ini du personnage PacMan
		pacMan = new PacMan(pacManX,pacManY,branchePacMan);

		// FANTOMES
		int fantomeX = map.getSpawnFantomeX();
		int fantomeY = map.getSpawnFantomeY();
		Branche brancheFantome = graphe.getBranche(fantomeX, fantomeY);
		// Ini des personnages Fantomes
		fantomes = new ArrayList<Fantome>();
		fantomes.add(new Blinky(fantomeX,fantomeY,brancheFantome,pacMan));
		fantomes.add(new Pinky(fantomeX,fantomeY,brancheFantome,pacMan));
		fantomes.add(new Inky(fantomeX,fantomeY,brancheFantome,pacMan));
		fantomes.add(new Clyde(fantomeX,fantomeY,brancheFantome,pacMan));

		score = 0;
		mangerDeSuite = 0;
		nombreDeTour = 0;
	}

	// Orientation de pacMan à chaque noeud
	public void destinationPersonnages() {
		pacMan.destination();
		//pacMan.destinationBranche();

		for (Fantome f: fantomes) {
			f.destination();
		}
	}

	// Deplacement d'une case de chaque personnage
	public void deplacementPersonnages() {
		pacMan.deplacement();
		for (Fantome f: fantomes) {
			f.deplacement();
		}
	}


	public void trouverCheminBlinky() {
		fantomes.get(BLINKY).trouverChemin();
		fantomes.get(BLINKY).prochaineDirection();
	}

	// Manger les pacGomme
	public void manger() {
		this.mangerGomme();
		this.mangerPacMan();
		this.mangerFantome();
	}

	public void mangerGomme() {
		int x = pacMan.getPositionX();
		int y = pacMan.getPositionY();
		int type = map.getCase(x, y);

		if (type==Map.GOMME) {
			map.mangerGomme(x,y);
			score+=SCORE_GOMME;

			setChanged();
			notifyObservers("G");
		} else if (type==Map.SUPERGOMME) {
			map.mangerSuperGomme(x,y);
			score+=SCORE_SUPERGOMME;

			pacMan.invulnerable();
			pacMan.tempsInvulnerabilite = Horloge.getTemps();
			for (Fantome fantome: fantomes) {
				if(fantome.estEnJeu()) {
					fantome.vulnerable();
				}
			}

			setChanged();
			notifyObservers("SG");
		}
	}

	public void mangerPacMan() {
		if(!pacMan.estInvulnerable()) {
			int x = pacMan.getPositionX();
			int y = pacMan.getPositionY();

			for (Fantome fantome: fantomes) {
				// On regarde si le fantome est invulnérable pour manger PacMan
				if (fantome.estInvulnerable() && fantome.estEnJeu()) {
					if (x==fantome.getPositionX() && y==fantome.getPositionY()) {
						pacMan.perteVie();
					}
				}
			}
		}
	}

	public void mangerFantome() {
		if(pacMan.estInvulnerable()) {
			int x = pacMan.getPositionX();
			int y = pacMan.getPositionY();

			for (Fantome fantome: fantomes) {
				// On regarde si le fantome est vulnérable
				if (!fantome.estInvulnerable() && fantome.estEnJeu()) {
					if (x==fantome.getPositionX() && y==fantome.getPositionY()) {
						fantome.mort();
						//this.mangerDeSuite++;
						this.score += SCORE_FANTOME*(Math.pow(2,mangerDeSuite-1));
					}
				}
			}
		}
	}

	// ----------------------------------------
	// Getteur
	public Map getMap() {
		return map;
	}
	public int getScore() {
		return score;
	}
	public PacMan getPM() {
		return pacMan;
	}
	public int getNombreDeTour() {
		return nombreDeTour;
	}

	
	
	// ----------------------------------------
	// Setteur
	public void directionPersonnage(int direction, int personnage) {
		if (personnage==PACMAN) {
			pacMan.direction(direction);
		} else {
			if(direction==Personnage.HAUT) {
				fantomes.get(personnage).directionHaut();
			} else if(direction==Personnage.DROITE) {
				fantomes.get(personnage).directionDroite();
			} else if(direction==Personnage.BAS) {
				fantomes.get(personnage).directionBas();
			} else if(direction==Personnage.GAUCHE) {
				fantomes.get(personnage).directionGauche();
			}
		}
	}

	public Fantome getFantome(int personnage) {
		return fantomes.get(personnage);
	}

	public void tourDeJeu() {
		//
		this.finEffetSuperGomme();
		// On libère un fantome avec 4sec en prison
		this.liberationFantome();
		
		//
		fantomes.get(BLINKY).decisionDirection();
		
		// Permet l'orientation au noeud
		this.destinationPersonnages();
		// on dit à pacMan d'y aller
		this.deplacementPersonnages();
		// et on mange sur notre chemin
		this.manger();
		
		nombreDeTour++;
	}

	public boolean finDePartie() {
		if(!pacMan.estEnJeu() || map.getNbGomme()+map.getNbSuperGomme()==0) {
			System.out.println("FIN DE LA PARTIE");
			return false;
		} else {
			return true;
		}
	}

	private void liberationFantome() {
		Fantome fantome = fantomes.get(BLINKY);

		if (!fantome.estEnJeu() && Horloge.getTemps()>fantome.dateSortie+4000) {
			fantome.enJeu();
		}
	}

	public void finEffetSuperGomme() {
		if (pacMan.estInvulnerable() && Horloge.getTemps()>pacMan.tempsInvulnerabilite+5000) {
			pacMan.vulnerable();
			for (Fantome fantome: fantomes) {
				if(fantome.estEnJeu()) {
					fantome.invulnerable();
				}
			}
		}
	}
}