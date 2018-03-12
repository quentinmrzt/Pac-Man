package model;

import java.util.Observable;

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

	private int vie;
	private int direction;
	private int prochaineDirection;
	private boolean invulnerable;
	private boolean enJeu;

	public Personnage(int v, int x, int y, Branche b) {
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
		this.vie = v;
		this.direction = STATIQUE;
		this.prochaineDirection = STATIQUE;
	}

	// GETTEUR
	public boolean estEnJeu() {
		return enJeu;
	}
	public Noeud getNoeud() {
		return noeud;
	}
	public int getVie() {
		return vie;
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
	public String getDirectionStr() {
		if (direction==HAUT) {
			return "Haut";
		} else if(direction==DROITE) {
			return "Droite";
		} else if(direction==BAS) {
			return "Bas";
		} else if(direction==GAUCHE) {
			return "Gauche";
		} else if(direction==STATIQUE) {
			return "Statique";
		} else {
			return null;
		}
	}
	public String getProchaineDirectionStr() {
		if (prochaineDirection==HAUT) {
			return "Haut";
		} else if(prochaineDirection==DROITE) {
			return "Droite";
		} else if(prochaineDirection==BAS) {
			return "Bas";
		} else if(prochaineDirection==GAUCHE) {
			return "Gauche";
		} else if(prochaineDirection==STATIQUE) {
			return "Statique";
		} else {
			return null;
		}
	} 

	// Implémentation de la branche
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
	public void setNoeud(Noeud noeud) {
		this.noeud = noeud;
	}
	public void perteVie() {
		vie--;

		setChanged();
		notifyObservers("VIE");
	}
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
	}
	public void vulnerable() {
		invulnerable = false;
	}
	public void enJeu() {
		enJeu = true;
	}
	public void horsJeu() {
		enJeu = false;
	}

	// FONCTION
	public boolean estInvulnerable() {
		return invulnerable;
	}

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
	public void destinationBranche() {
		Noeud desti = this.getNoeudDestination();

		if (desti==null) {
			// On est entre deux noeuds
		} else {
			// Position de la destination avec le noeud
			int destiX = desti.getX();
			int destiY = desti.getY();

			boolean reorientation = false;

			// si le perso se trouve à sa destination 
			if (positionX == destiX && positionY == destiY) {
				//this.decisionDirection(); ***********************************************************

				// On test s'il doit prendre une nouvelle direction..
				if (prochaineDirection==HAUT) {
					if (desti.getHaut()!=null) { // si la branche n'est pas vide
						direction = HAUT;
						prochaineDirection = STATIQUE;
						branche = desti.getHaut();
						reorientation = true;
					}
				} else if(prochaineDirection==DROITE) {
					if (desti.getDroite()!=null) { // si la branche n'est pas vide
						direction = DROITE;
						prochaineDirection = STATIQUE;
						branche = desti.getDroite();
						reorientation = true;
					}
				} else if(prochaineDirection==BAS) {
					if (desti.getBas()!=null) { // si la branche n'est pas vide
						direction = BAS;
						prochaineDirection = STATIQUE;
						branche = desti.getBas();
						reorientation = true;
					}
				} else if(prochaineDirection==GAUCHE) {
					if (desti.getGauche()!=null) { // si la branche n'est pas vide
						direction = GAUCHE;
						prochaineDirection = STATIQUE;
						branche = desti.getGauche();
						reorientation = true;
					}
				}

				// ..et s'il n'y a pas eu de réorientation, on continue notre chemin dans la même direction
				if (!reorientation) {
					if (direction==HAUT) {
						if (desti.getHaut()!=null) { // si la branche n'est pas vide
							branche = desti.getHaut();
						} else {
							direction = STATIQUE;
						}
					} else if(direction==DROITE) {
						if (desti.getDroite()!=null) { // si la branche n'est pas vide
							branche = desti.getDroite();
						} else {
							direction = STATIQUE;
						}
					} else if(direction==BAS) {
						if (desti.getBas()!=null) { // si la branche n'est pas vide
							branche = desti.getBas();
						} else {
							direction = STATIQUE;
						}
					} else if(this.getDirection()==GAUCHE) {
						if (desti.getGauche()!=null) { // si la branche n'est pas vide
							branche = desti.getGauche();
						} else {
							direction = STATIQUE;
						}
					}
				}
			}
		}
	}

	// Se deplace selon la direction du personnage
	public void deplacement() {
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

	public String toString() {		
		return "X: " + positionX + ", Y: " + positionY + ", direction: "+ getDirectionStr() + ", prochaineDirection: " + getProchaineDirectionStr()+".";
	}

}
