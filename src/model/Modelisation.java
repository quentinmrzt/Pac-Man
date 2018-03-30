package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import graphe.Branche;
import graphe.Graphe;

public class Modelisation extends Observable implements Observer {
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

	public Graphe getGraphe() {
		return graphe;
	}

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

		// Le model surveille différent élément
		pacMan.addObserver(this);
		map.addObserver(this);

		this.getFantome(Modelisation.BLINKY).addObserver(this);
		this.getFantome(Modelisation.PINKY).addObserver(this);
		this.getFantome(Modelisation.INKY).addObserver(this);
		this.getFantome(Modelisation.CLYDE).addObserver(this);
	}

	// ----------------------------------------
	// Getteur
	public Map getMap() {return map;}
	public int getScore() {return score;}
	public PacMan getPM() {return pacMan;}
	public int getNombreDeTour() {return nombreDeTour;}
	public Fantome getFantome(int personnage) {return fantomes.get(personnage);}

	// ----------------------------------------
	// TOUR DE JEU
	public void jeu() {
		while (this.finDePartie()) {
			this.tourDeJeu();

			try {
				Thread.sleep(80);
			} catch(InterruptedException e) { 
				System.err.println("ERREUR: Problème sur l'horloge.");
			}

			this.nombreDeTour++;
		}
	}

	public boolean finDePartie() {
		if(!pacMan.estEnJeu() || map.getNbGomme()+map.getNbSuperGomme()==0) {
			System.out.println("FIN DE LA PARTIE");
			return false;
		} else {
			return true;
		}
	}

	public void tourDeJeu() {
		// 1 - finEffetSuperGomme
		// + 60 = 5 secondes 
		if (pacMan.estInvulnerable() && nombreDeTour>pacMan.getTourInvulnerabilite()+60) {
			pacMan.vulnerable();
			for (Fantome fantome: fantomes) {
				if(fantome.estEnJeu()) {
					fantome.invulnerable(nombreDeTour);
				}
			}
		}

		// 2 - On libère un fantome avec 4sec en prison
		for (Fantome f: fantomes) {
			// + 25 = 2 secondes
			if (!f.estEnJeu()&& nombreDeTour>f.getEntreeEnJeu() && nombreDeTour>f.getTourEnJeu()+25) {
				f.enJeu(nombreDeTour);
			}
			
			//f.trouverChemin();
			f.decisionDirection();
		}

		// 3 - Recherche du chemin et direction
		//fantomes.get(BLINKY).trouverChemin();
		//fantomes.get(BLINKY).decisionDirection();

		// 4 - Permet l'orientation au noeud
		pacMan.destination();

		for (Fantome f: fantomes) {
			f.destination();
		}


		// FANTOMES: Ils se déplace et mangent
		for (Fantome f: fantomes) {
			f.deplacement();
		}
		this.mangerFantome();
		this.mangerPacMan();

		// PAC-MAN: Il se déplace et mange
		pacMan.deplacement();
		this.mangerFantome();
		this.mangerPacMan();
		this.mangerGomme();



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

			setChanged();
			notifyObservers("G");
		} else if (type==Map.SUPERGOMME) {
			map.mangerSuperGomme(x,y);
			score+=SCORE_SUPERGOMME;

			pacMan.invulnerable(nombreDeTour);
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
						pacMan.perteVie(nombreDeTour);
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
						fantome.mort(nombreDeTour);
						//this.mangerDeSuite++;
						this.score += SCORE_FANTOME*(Math.pow(2,mangerDeSuite-1));
					}
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

	public void trouverCheminBlinky() {
		fantomes.get(BLINKY).trouverChemin();
	}

	// Si des sous élements du model son maj
	public void update(Observable o, Object arg) {
		setChanged();

		if(o instanceof Blinky) {
			notifyObservers("Blinky");
		} else if(o instanceof Pinky) {
			notifyObservers("Pinky");
		} else if(o instanceof Inky) {
			notifyObservers("Inky");
		} else if(o instanceof Clyde) {
			notifyObservers("Clyde");
		} else if(o instanceof PacMan) {
			notifyObservers("PacMan");
		} else if(o instanceof Map) {
			notifyObservers("Map");
		} else {
			notifyObservers("TEST");
		}
	}
}