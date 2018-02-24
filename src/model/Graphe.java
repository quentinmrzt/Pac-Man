package model;

public class Graphe {
	private Noeud tabNoeud[][];
	private int taille = 0;

	// CONSTRUCTEUR	
	public Graphe(Map map) {
		// On simplie la map déjà existante 
		Map tmpMap = new Map(map);
		tmpMap.simplification();

		// Un tableau de noeud nous permettant de construire le graphe
		tabNoeud = new Noeud[map.getTailleX()][map.getTailleY()];

		// On récupérer les différents Noeuds ..
		for (int y=0 ; y<tmpMap.getTailleY() ; y++) {			
			for (int x=0 ; x<tmpMap.getTailleX() ; x++) {
				if(tmpMap.getCase(x, y) == map.SOL) {
					if (tmpMap.isIntersection(x, y)) {
						tabNoeud[x][y] = new Noeud(x,y);
						taille++;
					}
				}
			}
		}
		
		// .. on rajoute les spawns ..
		tabNoeud[map.getSpawnPacManX()][map.getSpawnPacManY()] = new Noeud(map.getSpawnPacManX(),map.getSpawnPacManY());
		tabNoeud[map.getSpawnFantomeX()][map.getSpawnFantomeY()] = new Noeud(map.getSpawnFantomeX(),map.getSpawnFantomeY());

		// .. et on les lies entres eux
		for (int y=0 ; y<tmpMap.getTailleY() ; y++) {
			for (int x=0 ; x<map.getTailleX() ; x++) {
				if (tabNoeud[x][y] != null) {
					boolean haut = true;
					boolean droite = true;
					boolean bas = true;
					boolean gauche = true;

					int i = 1;
					// Tant qu'on a pas testé toute les directions
					while(haut || droite || bas || gauche) {
						// HAUT: 
						if (haut) {
							if (tmpMap.getCase(x, y-i) != tmpMap.SOL) {
								haut = false;
							} else {
								if (tabNoeud[x][y-i] != null) {
									tabNoeud[x][y].setHaut(tabNoeud[x][y-i]);
									haut = false;
								}
							}
						}

						// DROITE:
						if (droite) {
							if (tmpMap.getCase(x+i, y) != tmpMap.SOL) {
								droite = false;
							} else {
								if (tabNoeud[x+i][y] != null) {
									tabNoeud[x][y].setDroite(tabNoeud[x+i][y]);
									droite = false;
								}
							}

						}

						// BAS: 
						if(bas) {
							if (tmpMap.getCase(x, y+i) != tmpMap.SOL) {
								bas = false;
							} else {
								if (tabNoeud[x][y+i] != null) {
									tabNoeud[x][y].setBas(tabNoeud[x][y+i]);
									bas = false;
								}
							}

						}

						// GAUCHE
						if (gauche) {
							if (tmpMap.getCase(x-i, y) != tmpMap.SOL) {
								gauche = false;
							} else {
								if (tabNoeud[x-i][y] != null) {
									tabNoeud[x][y].setGauche(tabNoeud[x-i][y]);
									gauche = false;
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
	public int getTaille() {
		return taille;
	}
	public Noeud getNoeud(int x, int y) {
		return tabNoeud[x][y];
	}
}
