package model;

import java.util.Observable;
import java.util.Scanner;

public class Modelisation extends Observable {
	// ----------------------------------------
	// Donnée du model
	protected Map map;
	protected PacMan pacMan;
	protected Graphe graphe;
	protected int test = 0;

	public Modelisation() {
		int pacManX = 1;
		int pacManY = 1;

		map = new Map("src/map_gomme.txt");
		graphe = new Graphe(map, pacManX, pacManY);
		pacMan = new PacMan(pacManX, pacManY);
	}

	public boolean deplacementPMHaut() {
		// Prochaine direction de PacMan
		pacMan.setProchaineDirection(pacMan.HAUT);

		// Si pacMan va en bas changement de direction direct (cas de changement de direction dans un couloir)
		if (pacMan.getDirection() == pacMan.BAS && graphe.deplacementHaut()) {
			return true;
		} else {
			return false;
		}
	}
	public boolean deplacementPMDroite() {
		// Prochaine direction de PacMan
		pacMan.setProchaineDirection(pacMan.DROITE);

		// Si pacMan va a gauche changement de direction direct 
		if (pacMan.getDirection() == pacMan.GAUCHE && graphe.deplacementDroite()) {
			return true;
		} else {
			return false;
		}
	}
	public boolean deplacementPMBas() {
		// Prochaine direction de PacMan
		pacMan.setProchaineDirection(pacMan.BAS);

		// Si pacMan va en bas changement de direction direct 
		if (pacMan.getDirection() == pacMan.HAUT && graphe.deplacementBas()) {
			return true;
		} else {
			return false;
		}
	}
	public boolean deplacementPMGauche() {
		// Prochaine direction de PacMan
		pacMan.setProchaineDirection(pacMan.GAUCHE);

		// Si pacMan va a droite changement de direction direct
		if (pacMan.getDirection() == pacMan.DROITE && graphe.deplacementGauche()) {
			return true;
		} else {
			return false;
		}
	}

	// PacMan se deplace dans le tableau selon sa prochaine destination
	// On part du principte que sa destination est horizontal ou vertical à lui
	public void deplacementPacMan() {
		// Position de PacMan
		int xPM = pacMan.getPositionX();
		int yPM = pacMan.getPositionY();
		// Position de la destination de PacMan
		int xG = graphe.getPosActuelle().getX();
		int yG = graphe.getPosActuelle().getY();

		if (yPM > yG) {
			// HAUT
			pacMan.enHaut();

			setChanged();
			notifyObservers();
		} else if(xPM < xG) {
			// DROITE
			pacMan.aDroite();

			setChanged();
			notifyObservers();
		} else if(yPM < yG) {
			// BAS
			pacMan.enBas();

			setChanged();
			notifyObservers();
		} else if(xPM > xG) {
			// GAUCHE
			pacMan.aGauche();

			setChanged();
			notifyObservers();
		}
	}

	// Orientation de pacMan à chaque noeud
	public void destinationPacMan() {
		// Position de PacMan
		int xPM = pacMan.getPositionX();
		int yPM = pacMan.getPositionY();
		// Position de la destination de PacMan
		int xG = graphe.getPosActuelle().getX();
		int yG = graphe.getPosActuelle().getY();
		boolean reorientation = false;

		// si PacMan se trouve sur un noeud
		if (xPM == xG && yPM == yG) {
			// On test s'il doit prendre une nouvelle direction..
			if (pacMan.getProchaineDirection()==pacMan.HAUT) {
				// HAUT
				if(graphe.deplacementHaut()) {
					reorientation = true;
				}
			} else if(pacMan.getProchaineDirection()==pacMan.DROITE) {
				// DROITE
				if(graphe.deplacementDroite()) {
					reorientation = true;
				}
			} else if(pacMan.getProchaineDirection()==pacMan.BAS) {
				// BAS
				if(graphe.deplacementBas()) {
					reorientation = true;
				}
			} else if(pacMan.getProchaineDirection()==pacMan.GAUCHE) {
				// GAUCHE
				if(graphe.deplacementGauche()) {
					reorientation = true;
				}
			}

			// ..et s'il n'y a pas eu de réorientation, on continue notre chemin dans la même direction
			if (!reorientation) {
				if (pacMan.getDirection()==pacMan.HAUT) {
					// HAUT
					if(!graphe.deplacementHaut()) {
						pacMan.setProchaineDirection(pacMan.STATIQUE);
					}
				} else if(pacMan.getDirection()==pacMan.DROITE) {
					// DROITE
					if(!graphe.deplacementDroite()) {
						pacMan.setProchaineDirection(pacMan.STATIQUE);
					}
				} else if(pacMan.getDirection()==pacMan.BAS) {
					// BAS
					if(!graphe.deplacementBas()) {
						pacMan.setProchaineDirection(pacMan.STATIQUE);
					}
				} else if(pacMan.getDirection()==pacMan.GAUCHE) {
					// GAUCHE
					if(!graphe.deplacementGauche()) {
						pacMan.setProchaineDirection(pacMan.STATIQUE);
					}
				} else {
					// PacMan n'est pas en mouvement
				}
			}
		} else {
			// PacMan est déjà en mouvement
		}
	}

	// ----------------------------------------
	// Getteur
	public int getTest() {
		return test;
	}
	public Map getMap() {
		return map;
	}
	public PacMan getPM() {
		return pacMan;
	}


	// ----------------------------------------
	// Setteur
	public void setTest(int i) {
		test = i;
		setChanged();
		notifyObservers(this.test);
	}



	public static void main(String[] args) {
		Modelisation modelisation = new Modelisation();

		Scanner s = new Scanner(System.in);
		String str = "";

		boolean fin = false;
		while (!fin) {
			System.out.println(modelisation.graphe.getPosActuelle().toString());
			System.out.println(modelisation.pacMan.toString());

			System.out.print("Rien faire (r) / Haut (h) / Droite (d) / Bas (b) / Gauche (g) / Fin (f) = ");
			str = s.next();

			// 	CONTROLEUR
			if(str.equals("h")) {
				modelisation.deplacementPMHaut();
			} else if (str.equals("d")) {
				modelisation.deplacementPMDroite();
			} else if (str.equals("b")) {
				modelisation.deplacementPMBas();
			} else if (str.equals("g")) {
				modelisation.deplacementPMGauche();
			} else if (str.equals("f")) {
				System.out.println("Bye.");
				fin = true;
			} else if(str.equals("r")) {
				// Ne rien faire
			} else {
				System.out.println("ERREUR DE FRAPPE !");
			}

			System.out.println("Direction: "+modelisation.pacMan.getDirection()+". Prochaine: "+modelisation.pacMan.getProchaineDirection());

			// Permet l'orientation au noeud
			modelisation.destinationPacMan();
			// et on dit à pacMan d'y aller
			modelisation.deplacementPacMan();

			System.out.println(modelisation.pacMan.toString());
			System.out.println("");
		}

		s.close();
	}	

}