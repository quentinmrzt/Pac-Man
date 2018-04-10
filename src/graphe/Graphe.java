package graphe;

import model.Map;

public class Graphe {
	private Noeud tabNoeud[][];
	private int tailleX, tailleY;

	// CONSTRUCTEUR	
	public Graphe(Map map) {
		// Un tableau de noeud nous permettant de construire le graphe
		this.tailleX = map.getTailleX();
		this.tailleY = map.getTailleY();
		
		this.tabNoeud = new Noeud[tailleX][tailleY];

		// On r�cup�rer les diff�rents Noeuds ..
		for (int y=0 ; y<map.getTailleY() ; y++) {			
			for (int x=0 ; x<map.getTailleX() ; x++) {
				if (map.isIntersection(x, y)) {
					tabNoeud[x][y] = new Noeud(x,y);
				}
			}
		}

		// .. et on les lies entres eux
		for (int y=0 ; y<map.getTailleY() ; y++) {
			for (int x=0 ; x<map.getTailleX() ; x++) {
				if (this.noeudExiste(x,y)) {
					// On v�rifie que le lien n'a pas d�j� �t� fais
					boolean haut = !tabNoeud[x][y].existeHaut();
					boolean droite = !tabNoeud[x][y].existeDroite();
					boolean bas = !tabNoeud[x][y].existeBas();
					boolean gauche = !tabNoeud[x][y].existeGauche();

					int i = 1;
					// Tant qu'on a pas test� toute les directions
					while(haut || droite || bas || gauche) {
						// HAUT
						if (haut) {
							if (map.getCase(x, y-i) == Map.MUR) {
								haut = false;
							} else {
								if (this.noeudExiste(x,y-1)) {
									// on cr�� une branche: en haut (n1), en bas (n2)
									Branche tmp = new Branche(tabNoeud[x][y-i],tabNoeud[x][y]);
									tabNoeud[x][y].setHaut(tmp);
									tabNoeud[x][y-i].setBas(tmp);
									haut = false; // le lien a �t� �tablit 
								}
							}
						}

						// DROITE
						if (droite) {
							if (map.getCase(x+i, y) == Map.MUR) {
								droite = false;
							} else {
								if (this.noeudExiste(x+i,y)) {
									// on cr�� une branche: � gauche (n1), � droite (n2)
									Branche tmp = new Branche(tabNoeud[x][y],tabNoeud[x+i][y]);
									tabNoeud[x][y].setDroite(tmp);
									tabNoeud[x+i][y].setGauche(tmp);
									droite = false; // le lien a �t� �tablit 
								}
							}
						}

						// BAS
						if(bas) {
							if (map.getCase(x, y+i) == Map.MUR) {
								bas = false;
							} else {
								if (this.noeudExiste(x,y+i)) {
									// on cr�� une branche: en haut (n1), en bas (n2)
									Branche tmp = new Branche(tabNoeud[x][y],tabNoeud[x][y+i]);
									tabNoeud[x][y].setBas(tmp);
									tabNoeud[x][y+i].setHaut(tmp);
									bas = false; // le lien a �t� �tablit 
								}
							}

						}

						// GAUCHE
						if (gauche) {
							if (map.getCase(x-i, y) == Map.MUR) {
								gauche = false;
							} else {
								if (this.noeudExiste(x-i,y)) {
									// on cr�� une branche: � gauche (n1), � droite (n2)
									Branche tmp = new Branche(tabNoeud[x-i][y],tabNoeud[x][y]);
									tabNoeud[x][y].setDroite(tmp);
									tabNoeud[x-i][y].setGauche(tmp);
									gauche = false; // le lien a �t� �tablit 
								}
							}
						}

						i++;
					}
				}
			}
		}		
	}

	// GETTEUR
	public Noeud getNoeud(int x, int y) {return tabNoeud[x][y];}
	public int getTailleX() {return tailleX;}
	public int getTailleY() {return tailleY;}

	/**
	 * Fonction permettant de trouver la branche � l'aide d'un point de la map
	 * @param x La coordonn�e x
	 * @param y La coordonn�e y
	 * @return Retourne la branche sur laquelle on se trouve
	 */
	public Branche getBranche(int x, int y) {
		int tailleX = tabNoeud.length;
		int tailleY = tabNoeud[0].length;

		if (x<0 || y<0 || x>=tailleX || y>=tailleY) {
			System.err.println("ERREUR: Valeur hors tableau [getBranche("+x+","+y+")].");
			return null;
		}

		if (this.noeudExiste(x,y)) {
			// si on se trouve sur un noeud
			if(tabNoeud[x][y].existeHaut()) {
				return tabNoeud[x][y].getHaut();
			} else if(tabNoeud[x][y].existeDroite()) {
				return tabNoeud[x][y].getDroite();
			} else if(tabNoeud[x][y].existeBas()) {
				return tabNoeud[x][y].getBas();
			} else if(tabNoeud[x][y].existeGauche()) {
				return tabNoeud[x][y].getGauche();
			} else {
				System.err.println("ERREUR: le noeud n'a pas de voisin.");
			}
		} else {
			// Cela va nous servir pour tester les bornes et les directions d�j� effectu�s
			boolean haut = y>0;
			boolean droite = x<tailleX-1;
			boolean bas = y<tailleY-1;
			boolean gauche = x>0;

			int i = 1;
			// Tant qu'on a pas test� toute les directions
			while(haut || droite || bas || gauche) {
				//System.out.println("Haut:"+haut+" Droite:"+droite+" Bas:"+bas+" Gauche:"+gauche+".");

				// HAUT
				if (haut) {
					if (this.noeudExiste(x, y-i)) {
						if(tabNoeud[x][y-i].existeBas()) {
							return tabNoeud[x][y-i].getBas();
						} else {
							// il n'y a pas de lien en haut
							haut = false;
						}
					}
				}

				// DROITE
				if (droite) {
					if (this.noeudExiste(x+i, y)) {
						if(tabNoeud[x+i][y].existeGauche()) {
							return tabNoeud[x+i][y].getGauche();
						} else {
							// il n'y a pas de lien � droite
							droite = false;
						}
					}
				}

				// BAS
				if(bas) {
					if (this.noeudExiste(x, y+i)) {
						if(tabNoeud[x][y+i].existeHaut()) {
							return tabNoeud[x][y+i].getHaut();
						} else {
							// il n'y a pas de lien en haut
							bas = false;
						}
					}
				}

				// GAUCHE
				if (gauche) {
					if (this.noeudExiste(x-i, y)) {
						if(tabNoeud[x-i][y].existeDroite()) {
							return tabNoeud[x-i][y].getDroite();
						} else {
							// il n'y a pas de lien en haut
							gauche = false;
						}
					}
				}

				// On augmente le pas
				i++;

				// On v�rifie qu'on ne sort pas des limites
				if (y-i<0) {
					haut = false;
				}
				if (x+i>=tailleX) {
					droite = false;
				}
				if (y+i>=tailleY) {
					bas = false;
				}
				if (x-i<0) {
					gauche = false;
				}
			}
		}

		return null;
	}

	// TEST
	public boolean noeudExiste(int x, int y) {
		return tabNoeud[x][y]!=null;
	}
}
