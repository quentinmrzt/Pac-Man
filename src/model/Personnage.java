package model;

import java.util.Observable;

import graphe.Branche;
import graphe.Noeud;
import jeu.Horloge;

public abstract class Personnage extends Observable {
	public final static int HAUT = 0;
	public final static int DROITE = 1;
	public final static int BAS = 2;
	public final static int GAUCHE = 3;
	public final static int STATIQUE = 4;

	private int positionDepartX;
	private int positionDepartY;
	private Branche brancheDepart;
	private Noeud noeudDepart;

	// Position courante
	private int positionX;
	private int positionY;
	private Branche branche;
	private Noeud noeud;

	private int direction;
	private int prochaineDirection;
	private boolean invulnerable;
	private boolean enJeu;

	public Personnage(int x, int y, Branche b) {
		// Position initiale
		this.positionDepartX = x;
		this.positionDepartY = y;
		this.brancheDepart = b;
		this.noeudDepart = b.getNoeud(x, y);

		// Position courante
		this.positionX = positionDepartX;
		this.positionY = positionDepartY;
		this.branche = brancheDepart;
		this.noeud = noeudDepart;

		// Caractéristiques
		this.direction = STATIQUE;
		this.prochaineDirection = STATIQUE;
	}

	// GETTEUR
	public boolean estInvulnerable() {
		return invulnerable;
	}
	public boolean estEnJeu() {
		return enJeu;
	}
	public Noeud getNoeud() {
		return noeud;
	}
	public int getPositionX() {
		return positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public int getDirection() {
		return direction;
	}
	public int getProchaineDirection() {
		return prochaineDirection;
	}
	/**
	 * Transforme une direction entière en chaine de caractère selon les constantes
	 * @param d La direction
	 * @return La traduction en string d'une direction
	 */
	public static String afficheDirection(int d) {
		if (d==HAUT) {
			return "Haut";
		} else if(d==DROITE) {
			return "Droite";
		} else if(d==BAS) {
			return "Bas";
		} else if(d==GAUCHE) {
			return "Gauche";
		} else if(d==STATIQUE) {
			return "Statique";
		} else {
			return null;
		}
	}
	
	public String getDirectionStr() {
		return afficheDirection(direction);
	}
	public String getProchaineDirectionStr() {
		return afficheDirection(prochaineDirection);
	} 
	
	/**
	 * Permet de determiner le noeud de notre destination selon notre direction
	 * Si nous somme sur un noeud, renvoi celui ci
	 * Si nous somme entre deux noeuds sans direction, renvoi le plus proche
	 * @return Le noeud vers lequel on se dirige
	 */
	public Noeud getNoeudDestination() {
		if (direction==HAUT || direction==GAUCHE) {
			return branche.getN1();
		} else if (direction==DROITE || direction==BAS) {
			return branche.getN2();
		} else {
			// DIRECTION STATIQUE: on ne sait pas d'ou l'on vient
			int n1X = branche.getN1().getX();
			int n1Y = branche.getN1().getY();
			int n2X = branche.getN2().getX();
			int n2Y = branche.getN2	().getY();

			if (positionX==n1X && positionY==n1Y) {
				// Si personnage sur N1
				return branche.getN1();
			} else if (positionX==n2X && positionY==n2Y) {
				// Si personnage sur N2
				return branche.getN2();
			} else {
				// On est à l'arrêt entre deux noeud: pas de destination
				int distance1 = Math.abs(positionX-n1X)+Math.abs(positionY-n1Y);
				int distance2 = Math.abs(positionX-n2X)+Math.abs(positionY-n2Y);

				// On renvoie le noeud le plus proche
				if (distance1 <= distance2) {
					return branche.getN1();
				} else {
					return branche.getN2();
				}
			}
		}
	}

	/**
	 * Permet de determiner le noeud de départ selon notre direction
	 * Si nous somme sur un noeud, renvoi l'autre
	 * Si nous somme entre deux noeuds sans direction, renvoi le plus éloigné
	 * @return Le noeud d'ou l'on vient
	 */
	public Noeud getNoeudDepart() {
		if (direction==HAUT || direction==GAUCHE) {
			return branche.getN2();
		} else if (direction==DROITE || direction==BAS) {
			return branche.getN1();
		} else {
			// DIRECTION STATIQUE: on ne sait pas d'ou l'on vient
			int n1X = branche.getN1().getX();
			int n1Y = branche.getN1().getY();
			int n2X = branche.getN2().getX();
			int n2Y = branche.getN2	().getY();

			if (positionX==n1X && positionY==n1Y) {
				// Si personnage sur N1
				return branche.getN2();
			} else if (positionX==n2X && positionY==n2Y) {
				// Si personnage sur N2
				return branche.getN1();
			} else {
				// On est à l'arrêt entre deux noeud: pas de destination
				int distance1 = Math.abs(positionX-n1X)+Math.abs(positionY-n1Y);
				int distance2 = Math.abs(positionX-n2X)+Math.abs(positionY-n2Y);

				// On renvoie le noeud le plus éloigné
				if (distance1 < distance2) {
					return branche.getN2();
				} else {
					return branche.getN1();
				}
			}
		}
	}

	// SETTEUR
	public void enHaut() {
		positionY--;

		setChanged();
		notifyObservers("Y");
	}
	public void aDroite() {
		positionX++;

		setChanged();
		notifyObservers("X");
	}
	public void enBas() {
		positionY++;

		setChanged();
		notifyObservers("Y");
	}
	public void aGauche() {
		positionX--;

		setChanged();
		notifyObservers("X");
	}
	
	public void invulnerable() {
		invulnerable = true;
		
		setChanged();
		notifyObservers("INVULNERABLE");
	}
	public void vulnerable() {
		invulnerable = false;
		
		setChanged();
		notifyObservers("VULNERABLE");
	}
	
	int dateEntree=-1;
	int dateSortie=-1;
	
	public void enJeu() {
		enJeu = true;
		dateEntree = Horloge.getTemps();
		
		setChanged();
		notifyObservers("ENJEU");
	}
	public void horsJeu() {
		enJeu = false;
		dateSortie = Horloge.getTemps();
		
		setChanged();
		notifyObservers("HORSJEU");
	}

	// FONCTION


	// Change la direction ou la prochaine direction
	public void directionHaut() {
		// Si on change de direction dans un couloir ou si on était à l'arrêt dans un couloir
		if (getDirection()==BAS || (getDirection()==STATIQUE && !branche.estHorizontal())) {
			direction = HAUT;
			prochaineDirection = STATIQUE;
		} else {
			prochaineDirection = HAUT;
		}
	}
	public void directionDroite() {
		// Si on change de direction dans un couloir ou si on était à l'arrêt dans un couloir
		if (getDirection()==GAUCHE || (getDirection()==STATIQUE && branche.estHorizontal())) {
			direction = DROITE;
			prochaineDirection = STATIQUE;
		} else {
			prochaineDirection = DROITE;
		}
	}
	public void directionBas() {
		// Si on change de direction dans un couloir ou si on était à l'arrêt dans un couloir
		if (getDirection()==HAUT || (getDirection()==STATIQUE && !branche.estHorizontal())) {
			direction = BAS;
			prochaineDirection = STATIQUE;
		} else {
			prochaineDirection = BAS;
		}
	}
	public void directionGauche() {
		// Si on change de direction dans un couloir ou si on était à l'arrêt dans un couloir
		if (getDirection()==DROITE || (getDirection()==STATIQUE && branche.estHorizontal())) {
			direction = GAUCHE;
			prochaineDirection = STATIQUE;
		} else {
			prochaineDirection = GAUCHE;
		}
	}

	// Orientation dans les noeuds avec le noeud
	/**
	 * Regarde si on est à son objectif et oriente selon la prochaine direction
	 */
	public void destinationBranche() {
		Noeud desti = this.getNoeudDestination();

		// Position de la destination avec le noeud
		int destiX = desti.getX();
		int destiY = desti.getY();

		boolean reorientation = false;

		// si le perso se trouve à sa destination 
		if (positionX == destiX && positionY == destiY) {
			// On test s'il doit prendre une nouvelle direction..
			if (prochaineDirection==HAUT && desti.existeHaut()) {
					direction = HAUT;
					prochaineDirection = STATIQUE;
					branche = desti.getHaut();
					reorientation = true;
			} else if(prochaineDirection==DROITE && desti.existeDroite()) {
					direction = DROITE;
					prochaineDirection = STATIQUE;
					branche = desti.getDroite();
					reorientation = true;
			} else if(prochaineDirection==BAS && desti.existeBas()) {
					direction = BAS;
					prochaineDirection = STATIQUE;
					branche = desti.getBas();
					reorientation = true;
			} else if(prochaineDirection==GAUCHE && desti.existeGauche()) {
					direction = GAUCHE;
					prochaineDirection = STATIQUE;
					branche = desti.getGauche();
					reorientation = true;
			}

			// ..et s'il n'y a pas eu de réorientation, on continue notre chemin dans la même direction
			if (!reorientation) {
				if (direction==HAUT) {
					if (desti.existeHaut()) {
						branche = desti.getHaut();
					} else {
						direction = STATIQUE;
					}
				} else if(direction==DROITE) {
					if (desti.existeDroite()) {
						branche = desti.getDroite();
					} else {
						direction = STATIQUE;
					}
				} else if(direction==BAS) {
					if (desti.existeBas()) {
						branche = desti.getBas();
					} else {
						direction = STATIQUE;
					}
				} else if(this.getDirection()==GAUCHE) {
					if (desti.existeGauche()) {
						branche = desti.getGauche();
					} else {
						direction = STATIQUE;
					}
				}
			}
		}
	}

	// Se deplace selon la direction du personnage
	public void deplacement() {
		if(this.enJeu) {
			if (direction==HAUT) {
				this.enHaut();
			} else if(direction==DROITE) {
				this.aDroite();
			} else if(direction==BAS) {
				this.enBas();
			} else if(direction==GAUCHE) {
				this.aGauche();
			}
		}
	}

	public String toString() {		
		return "X: " + this.getPositionX() + ", Y: " + this.getPositionY() + ", direction: "+ getDirectionStr() + ", prochaineDirection: " + getProchaineDirectionStr()+".";
	}
	

	public void reinitialisation() {
		// On réini les données généraliste
		this.positionX = positionDepartX;
		this.positionY = positionDepartY;
		this.branche = brancheDepart;
		this.noeud = noeudDepart;

		this.direction = STATIQUE;
		this.prochaineDirection = STATIQUE;
		
		setChanged();
		notifyObservers("REINI");
	}

	abstract void mort();
}
