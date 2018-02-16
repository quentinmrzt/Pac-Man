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
		map = new Map("src/map_gomme.txt");
		graphe = new Graphe(map);
		pacMan = new PacMan(graphe.getPosActuelle().getX(), graphe.getPosActuelle().getY());
	}

	public boolean deplacementPMHaut() {
		// Prochaine direction de PacMan
		pacMan.setProchaineDirection(pacMan.HAUT);
		
		// changement de direction direct si c'est possible
		if(graphe.deplacementHaut()) {
			return true;
		} else {
			System.out.println("Impossible.");
			return false;
		}
	}
	public boolean deplacementPMDroite() {
		// Prochaine direction de PacMan
		pacMan.setProchaineDirection(pacMan.DROITE);
		
		// changement de direction direct si c'est possible
		if(graphe.deplacementDroite()) {
			return true;
		} else {
			System.out.println("Impossible.");
			return false;
		}
	}
	public boolean deplacementPMBas() {
		// Prochaine direction de PacMan
		pacMan.setProchaineDirection(pacMan.BAS);
		
		// changement de direction direct si c'est possible
		if(graphe.deplacementBas()) {
			return true;
		} else {
			System.out.println("Impossible.");
			return false;
		}
	}
	public boolean deplacementPMGauche() {
		// Prochaine direction de PacMan
		pacMan.setProchaineDirection(pacMan.GAUCHE);
		
		// changement de direction direct si c'est possible
		if(graphe.deplacementGauche()) {
			return true;
		} else {
			System.out.println("Impossible.");
			return false;
		}
	}

	// PacMan se deplace dans le tableau selon sa prochaine destination
	public void deplacementPacMan() {
		int xPM = pacMan.getPositionX();
		int yPM = pacMan.getPositionY();
		int xG = graphe.getPosActuelle().getX();
		int yG = graphe.getPosActuelle().getY();

		if (yPM > yG) {
			// HAUT
			pacMan.enHaut();
		} else if(xPM < xG) {
			// DROITE
			pacMan.aDroite();
		} else if(yPM < yG) {
			// BAS
			pacMan.enBas();
		} else if(xPM > xG) {
			// GAUCHE
			pacMan.aGauche();
		} else {
			// NE RIEN FAIRE
		}
	}

	// Orientation de pacMan à chaque noeud
	public void destinationPacMan() {
		int xPM = pacMan.getPositionX();
		int yPM = pacMan.getPositionY();
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
	
	// ----------------------------------------
	// Setteur
	public void setTest(int i) {
		test = i;
		setChanged();
		notifyObservers(this.test);
	}
	
	
	
	public static void main(String[] args) {
		Modelisation monde = new Modelisation();

		Scanner s = new Scanner(System.in);
		String str = "";

		boolean fin = false;
		while (!fin) {
			System.out.println(monde.graphe.getPosActuelle().toString());
			System.out.println(monde.pacMan.toString());

			System.out.print("Rien faire (r) / Haut (h) / Droite (d) / Bas (b) / Gauche (g) / Fin (f) = ");
			str = s.next();

			// 	CONTROLEUR
			if(str.equals("h")) {
				monde.deplacementPMHaut();
			} else if (str.equals("d")) {
				monde.deplacementPMDroite();
			} else if (str.equals("b")) {
				monde.deplacementPMBas();
			} else if (str.equals("g")) {
				monde.deplacementPMGauche();
			} else if (str.equals("f")) {
				System.out.println("Bye.");
				fin = true;
			} else if(str.equals("r")) {
				// Ne rien faire
			} else {
				System.out.println("ERREUR DE FRAPPE !");
			}

			System.out.println("Direction: "+monde.pacMan.getDirection()+". Prochaine: "+monde.pacMan.getProchaineDirection());

			// Permet l'orientation au noeud
			monde.destinationPacMan();
			// et on dit à pacMan d'y aller
			monde.deplacementPacMan();

			System.out.println(monde.pacMan.toString());
			System.out.println("");
		}

		s.close();
	}	

}