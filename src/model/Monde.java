package model;

import java.util.Scanner;

public class Monde {
	protected Map map;
	protected PacMan pacMan;
	protected Graphe graphe;

	public Monde() {
		map = new Map();
		graphe = new Graphe(map);
		pacMan = new PacMan(graphe.getPosActuelle().getX(),graphe.getPosActuelle().getY());
	}

	public boolean deplacementHaut() {
		return graphe.deplacementHaut();
	}
	public boolean deplacementDroite() {
		return graphe.deplacementDroite();
	}
	public boolean deplacementBas() {
		return graphe.deplacementBas();
	}
	public boolean deplacementGauche() {
		return graphe.deplacementGauche();
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
						pacMan.prochaineDirection = pacMan.STATIQUE;
					}
				} else if(pacMan.getDirection()==pacMan.DROITE) {
					// DROITE
					if(!graphe.deplacementDroite()) {
						pacMan.prochaineDirection = pacMan.STATIQUE;
					}
				} else if(pacMan.getDirection()==pacMan.BAS) {
					// BAS
					if(!graphe.deplacementBas()) {
						pacMan.prochaineDirection = pacMan.STATIQUE;
					}
				} else if(pacMan.getDirection()==pacMan.GAUCHE) {
					// GAUCHE
					if(!graphe.deplacementGauche()) {
						pacMan.prochaineDirection = pacMan.STATIQUE;
					}
				} else {
					// PacMan n'est pas en mouvement
				}
			}
		} else {
			// PacMan est déjà en mouvement
		}
	}

	public static void main(String[] args) {
		Monde monde = new Monde();

		Scanner s = new Scanner(System.in);
		String str = "";

		boolean fin = false;
		while (!fin) {
			System.out.println(monde.graphe.getPosActuelle().toString());
			System.out.println(monde.pacMan.toString());

			System.out.print("Rien faire (r) / Haut (h) / Droite (d) / Bas (b) / Gauche (g) / Fin (f) = ");
			str = s.next();

			if(str.equals("h")) {
				// si on peut aller à haut
				monde.pacMan.prochaineDirection = monde.pacMan.HAUT;
				
				if(!monde.graphe.deplacementHaut()) {
					System.out.println("Impossible.");
				}
			} else if (str.equals("d")) {
				// si on peut aller à droite
				monde.pacMan.prochaineDirection = monde.pacMan.DROITE;
				if(!monde.graphe.deplacementDroite()) {
					System.out.println("Impossible.");
				}
			} else if (str.equals("b")) {
				// si on peut aller à bas
				monde.pacMan.prochaineDirection = monde.pacMan.BAS;
				if(!monde.graphe.deplacementBas()) {
					System.out.println("Impossible.");
				}
			} else if (str.equals("g")) {
				// si on peut aller à gauche
				monde.pacMan.prochaineDirection = monde.pacMan.GAUCHE;
				if(!monde.graphe.deplacementGauche()) {
					System.out.println("Impossible.");
				}
			} else if (str.equals("f")) {
				System.out.println("Bye.");
				fin = true;
			} else if(str.equals("r")) {

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
