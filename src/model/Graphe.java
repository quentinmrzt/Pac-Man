package model;

public class Graphe {
	private Noeud tabNoeud[][];
	private int taille = 0;

	// CONSTRUCTEUR	
	public Graphe(Map map) {
		// On simplie la map d�j� existante 
		Map tmpMap = new Map(map);
		tmpMap.simplification();

		// Un tableau de noeud nous permettant de construire le graphe
		tabNoeud = new Noeud[map.getTailleX()][map.getTailleY()];

		// On r�cup�rer les diff�rents Noeuds ..
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
					// On v�rifie que le lien n'a pas d�j� �t� fais
					boolean haut = tabNoeud[x][y].getHaut()==null;
					boolean droite = tabNoeud[x][y].getDroite()==null;
					boolean bas = tabNoeud[x][y].getBas()==null;
					boolean gauche = tabNoeud[x][y].getGauche()==null;

					int i = 1;
					// Tant qu'on a pas test� toute les directions
					while(haut || droite || bas || gauche) {
						// HAUT
						if (haut) {
							if (tmpMap.getCase(x, y-i) != tmpMap.SOL) {
								haut = false;
							} else {
								if (tabNoeud[x][y-i] != null) {
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
							if (tmpMap.getCase(x+i, y) != tmpMap.SOL) {
								droite = false;
							} else {
								if (tabNoeud[x+i][y] != null) {
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
							if (tmpMap.getCase(x, y+i) != tmpMap.SOL) {
								bas = false;
							} else {
								if (tabNoeud[x][y+i] != null) {
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
							if (tmpMap.getCase(x-i, y) != tmpMap.SOL) {
								gauche = false;
							} else {
								if (tabNoeud[x-i][y] != null) {
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
	public int getTaille() {
		return taille;
	}
	public Noeud getNoeud(int x, int y) {
		return tabNoeud[x][y];
	}
}
