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
				if(tmpMap.getCase(x, y) == Map.SOL) {
					if (tmpMap.isIntersection(x, y)) {
						tabNoeud[x][y] = new Noeud(x,y);
						taille++;
					}
				}
			}
		}

		// .. on rajoute les spawns ..
		//tabNoeud[map.getSpawnPacManX()][map.getSpawnPacManY()] = new Noeud(map.getSpawnPacManX(),map.getSpawnPacManY());
		tabNoeud[map.getSpawnFantomeX()][map.getSpawnFantomeY()] = new Noeud(map.getSpawnFantomeX(),map.getSpawnFantomeY());

		// .. et on les lies entres eux
		for (int y=0 ; y<tmpMap.getTailleY() ; y++) {
			for (int x=0 ; x<map.getTailleX() ; x++) {
				if (tabNoeud[x][y] != null) {
					// On vérifie que le lien n'a pas déjà été fais
					boolean haut = tabNoeud[x][y].getHaut()==null;
					boolean droite = tabNoeud[x][y].getDroite()==null;
					boolean bas = tabNoeud[x][y].getBas()==null;
					boolean gauche = tabNoeud[x][y].getGauche()==null;

					int i = 1;
					// Tant qu'on a pas testé toute les directions
					while(haut || droite || bas || gauche) {
						// HAUT
						if (haut) {
							if (tmpMap.getCase(x, y-i) != Map.SOL) {
								haut = false;
							} else {
								if (tabNoeud[x][y-i] != null) {
									// on créé une branche: en haut (n1), en bas (n2)
									Branche tmp = new Branche(tabNoeud[x][y-i],tabNoeud[x][y]);
									tabNoeud[x][y].setHaut(tmp);
									tabNoeud[x][y-i].setBas(tmp);
									haut = false; // le lien a été établit 
								}
							}
						}

						// DROITE
						if (droite) {
							if (tmpMap.getCase(x+i, y) != Map.SOL) {
								droite = false;
							} else {
								if (tabNoeud[x+i][y] != null) {
									// on créé une branche: à gauche (n1), à droite (n2)
									Branche tmp = new Branche(tabNoeud[x][y],tabNoeud[x+i][y]);
									tabNoeud[x][y].setDroite(tmp);
									tabNoeud[x+i][y].setGauche(tmp);
									droite = false; // le lien a été établit 
								}
							}
						}

						// BAS
						if(bas) {
							if (tmpMap.getCase(x, y+i) != Map.SOL) {
								bas = false;
							} else {
								if (tabNoeud[x][y+i] != null) {
									// on créé une branche: en haut (n1), en bas (n2)
									Branche tmp = new Branche(tabNoeud[x][y],tabNoeud[x][y+i]);
									tabNoeud[x][y].setBas(tmp);
									tabNoeud[x][y+i].setHaut(tmp);
									bas = false; // le lien a été établit 
								}
							}

						}

						// GAUCHE
						if (gauche) {
							if (tmpMap.getCase(x-i, y) != Map.SOL) {
								gauche = false;
							} else {
								if (tabNoeud[x-i][y] != null) {
									// on créé une branche: à gauche (n1), à droite (n2)
									Branche tmp = new Branche(tabNoeud[x-i][y],tabNoeud[x][y]);
									tabNoeud[x][y].setDroite(tmp);
									tabNoeud[x-i][y].setGauche(tmp);
									gauche = false; // le lien a été établit 
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
			// Cela va nous servir pour tester les bornes et les directions déjà effectués
			boolean haut = y>0;
			boolean droite = x<tailleX-1;
			boolean bas = y<tailleY-1;
			boolean gauche = x>0;

			int i = 1;
			// Tant qu'on a pas testé toute les directions
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
							// il n'y a pas de lien à droite
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

				// On vérifie qu'on ne sort pas des limites
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


	public static void main(String[] args) {
		Map map = new Map("src/map_gomme.txt");
		Graphe graphe = new Graphe(map);
		Branche branche;

		int x, y;

		// On test la borne en Haut à Gauche, pas sur un noeud, pas de noeud sur le chemin et donc pas de lien
		x=0; 
		y=0;
		branche = graphe.getBranche(x, y);
		if (branche!=null) {
			System.out.println("La branche en x:"+x+", y:"+y+" existe !");
		} else {
			System.out.println("La branche en x:"+x+", y:"+y+" n'existe pas !");
		}
		System.out.println("");

		// Sur le noeud (1,1): Droite et Bas
		x = 1;
		y = 1;
		branche = graphe.getBranche(x, y);
		if (branche!=null) {
			System.out.println("La branche en x:"+x+", y:"+y+" existe !");
			System.out.println("N1: ("+branche.getN1().getX()+","+branche.getN1().getY()+") et N2: ("+branche.getN2().getX()+","+branche.getN2().getY()+").");
		} else {
			System.out.println("La branche en x:"+x+", y:"+y+" n'existe pas !");
		}
		System.out.println("");

		// Sur le noeud (6,5): Haut, Droite, Bas et Gauche
		x = 6;
		y = 5;
		branche = graphe.getBranche(x, y);
		if (branche!=null) {
			System.out.println("La branche en x:"+x+", y:"+y+" existe !");
			System.out.println("N1: ("+branche.getN1().getX()+","+branche.getN1().getY()+") et N2: ("+branche.getN2().getX()+","+branche.getN2().getY()+").");
		} else {
			System.out.println("La branche en x:"+x+", y:"+y+" n'existe pas !");
		}
		System.out.println("");

		// Sur une branche (3,8): Droite et Gauche
		x = 3;
		y = 8;
		branche = graphe.getBranche(x, y);
		if (branche!=null) {
			System.out.println("La branche en x:"+x+", y:"+y+" existe !");
			System.out.println("N1: ("+branche.getN1().getX()+","+branche.getN1().getY()+") et N2: ("+branche.getN2().getX()+","+branche.getN2().getY()+").");
		} else {
			System.out.println("La branche en x:"+x+", y:"+y+" n'existe pas !");
		}
		System.out.println("");

		// Sur rien (1,14): avec un noeud qui ne le relie pas en Haut, à Droite et en Bas
		x = 1;
		y = 14;
		branche = graphe.getBranche(x, y);
		if (branche!=null) {
			System.out.println("La branche en x:"+x+", y:"+y+" existe !");
			System.out.println("N1: ("+branche.getN1().getX()+","+branche.getN1().getY()+") et N2: ("+branche.getN2().getX()+","+branche.getN2().getY()+").");
		} else {
			System.out.println("La branche en x:"+x+", y:"+y+" n'existe pas !");
		}
		System.out.println("");
	}
}
