package model;

import java.util.Observable;

import graphe.Branche;
import graphe.Noeud;

public abstract class Personnage extends Observable {
	public final static int HAUT = 0;
	public final static int DROITE = 1;
	public final static int BAS = 2;
	public final static int GAUCHE = 3;
	public final static int STATIQUE = 4;

	// Sauvegarde au départ
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
	public Branche getBranche() {return branche;}
	public boolean estEnJeu() {return enJeu;}
	public boolean estSurNoeud() {return noeud!=null;}
	public Noeud getNoeud() {return noeud;}
	public int getPositionX() {return positionX;}
	public int getPositionY() {return positionY;}
	public int getDirection() {return direction;}
	public int getProchaineDirection() {return prochaineDirection;}


	
	// Affichage des directions en string
	public String getDirectionStr() {
		return afficheDirection(direction);
	}
	public String getProchaineDirectionStr() {
		return afficheDirection(prochaineDirection);
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
	
	/**
	 * Donne la direction inverse
	 * @param La direction à inverser, exemple: Gauche
	 * @return La direction inversée, exemple: Droite
	 */
	public static int directionInverse(int d) {
		if (d==HAUT) {
			return BAS;
		} else if (d==DROITE) {
			return GAUCHE;
		} else if (d==BAS) {
			return HAUT;
		} else if (d==GAUCHE) {
			return DROITE;
		} else if (d==STATIQUE) {
			return STATIQUE;
		} else {
			return -1;
		}
	}

	/**
	 * Permet de determiner le noeud de notre destination selon notre direction
	 * Si le personnage est statique, renvoi null
	 * @return Le noeud vers lequel on se dirige
	 */
	public Noeud getNoeudDestination() {
		if (direction==HAUT || direction==GAUCHE) {
			return branche.getN1();
		} else if (direction==DROITE || direction==BAS) {
			return branche.getN2();
		} else {
			return null;
		}
	}

	/**
	 * Permet de determiner le noeud de départ selon notre direction
	 * Si le personnage est statique, renvoi null
	 * @return Le noeud d'ou l'on vient
	 */
	public Noeud getNoeudDepart() {
		if (direction==HAUT || direction==GAUCHE) {
			return branche.getN2();
		} else if (direction==DROITE || direction==BAS) {
			return branche.getN1();
		} else {
			return null;
		}
	}
	


	
	public void enJeu() {
		enJeu = true;

		setChanged();
		notifyObservers("ENJEU");
	}
	public void horsJeu() {
		enJeu = false;

		setChanged();
		notifyObservers("HORSJEU");
	}

	/**
	 * Fonction permettant de changer la direction du personnage
	 * @param La direction que l'on veut donner au personnage
	 */
	public void direction(int direction) {
		if (branche!=null) {
			// Changement direct si demi-tour ou statique
			if(this.direction == Personnage.directionInverse(direction) || (this.direction==STATIQUE && branche.dansLeSens(direction))) {
				this.direction = direction;
				this.prochaineDirection = STATIQUE;
			} else {
				this.prochaineDirection = direction;
			}
		} else if (noeud!=null) {
			this.direction = direction;
			this.prochaineDirection = STATIQUE;
		} else {
			System.err.println("ERREUR: branche et noeud.");
		}
	}

	/**
	 * Oriente aux noeuds
	 */
	public void destination() {
		if (noeud!=null) {
			boolean reorientation = false;
			
			// Si on peut changer de direction
			if (prochaineDirection==HAUT && noeud.existeHaut()) {
				direction = HAUT;
				prochaineDirection = STATIQUE;
				branche = noeud.getHaut();
				reorientation = true;
			} else if(prochaineDirection==DROITE && noeud.existeDroite()) {
				direction = DROITE;
				prochaineDirection = STATIQUE;
				branche = noeud.getDroite();
				reorientation = true;
			} else if(prochaineDirection==BAS && noeud.existeBas()) {
				direction = BAS;
				prochaineDirection = STATIQUE;
				branche = noeud.getBas();
				reorientation = true;
			} else if(prochaineDirection==GAUCHE && noeud.existeGauche()) {
				direction = GAUCHE;
				prochaineDirection = STATIQUE;
				branche = noeud.getGauche();
				reorientation = true;
			}

			// Si on peut continuer dans la même direction
			if (!reorientation) {
				if (direction==HAUT) {
					if (noeud.existeHaut()) {
						branche = noeud.getHaut();
					} else {
						direction = STATIQUE;
					}
				} else if(direction==DROITE) {
					if (noeud.existeDroite()) {
						branche = noeud.getDroite();
					} else {
						direction = STATIQUE;
					}
				} else if(direction==BAS) {
					if (noeud.existeBas()) {
						branche = noeud.getBas();
					} else {
						direction = STATIQUE;
					}
				} else if(this.getDirection()==GAUCHE) {
					if (noeud.existeGauche()) {
						branche = noeud.getGauche();
					} else {
						direction = STATIQUE;
					}
				}
			}
		}
	}
	
	/**
	 * Déplace le personnage selon sa direction
	 */
	public void deplacement() {
		if(this.enJeu) {
			if (direction==HAUT) {
				positionY--;
			} else if(direction==DROITE) {
				positionX++;
			} else if(direction==BAS) {
				positionY++;
			} else if(direction==GAUCHE) {
				positionX--;
			}
			
			noeud = branche.getNoeud(positionX,positionY);
			
			setChanged();
			notifyObservers("DEPLACEMENT");
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
