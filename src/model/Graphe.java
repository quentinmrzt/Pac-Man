package model;

public class Graphe {
	private Noeud posActuelle = null;
	private Noeud tabNoeud[][];
	private int taille = 0;

	// CONSTRUCTEUR	
	public Graphe(Noeud n) {
		posActuelle = n;
		taille = 1;
	}

	public Graphe(Map map, int pacManX, int pacManY) {
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
		
		// On place la position au bon endroit
		posActuelle = tabNoeud[pacManX][pacManY];
	}

	
	
	// GETTEUR
	public Noeud getPosActuelle() {
		return posActuelle;
	}
	public int getTaille() {
		return taille;
	}

	
	// SETTEUR
	public void addNoeudBas(Noeud n) {
		posActuelle.setBas(n);
		n.setHaut(posActuelle);
		posActuelle = n;

		taille++;
	}
	public void addNoeudHaut(Noeud n) {
		posActuelle.setHaut(n);
		n.setBas(posActuelle);
		posActuelle = n;

		taille++;
	}
	public void addNoeudDroite(Noeud n) {
		posActuelle.setDroite(n);
		n.setGauche(posActuelle);
		posActuelle = n;

		taille++;
	}
	public void addNoeudGauche(Noeud n) {
		posActuelle.setGauche(n);
		n.setDroite(posActuelle);
		posActuelle = n;

		taille++;
	}


	// DEPLACEMENT
	public boolean deplacementHaut() {
		if (posActuelle.getHaut() != null) {
			posActuelle = posActuelle.getHaut().getApres();

			return true;
		}

		return false;
	}
	public boolean deplacementDroite() {
		if (posActuelle.getDroite() != null) {
			posActuelle = posActuelle.getDroite().getApres();

			return true;
		}

		return false;
	}
	public boolean deplacementBas() {
		if (posActuelle.getBas() != null) {
			posActuelle = posActuelle.getBas().getApres();

			return true;
		}

		return false;
	}
	public boolean deplacementGauche() {
		if (posActuelle.getGauche() != null) {
			posActuelle = posActuelle.getGauche().getApres();

			return true;
		}

		return false;
	}

	
	public static void main(String args[]) {
		Graphe g = new Graphe(new Noeud(2,2));
		System.out.println(g.getPosActuelle().toString());

		g.addNoeudBas(new Noeud(6,2));
		System.out.println(g.getPosActuelle().toString());

		g.addNoeudBas(new Noeud(9,2));
		System.out.println(g.getPosActuelle().toString());

		g.deplacementHaut();
		System.out.println(g.getPosActuelle().toString());

		g.deplacementHaut();
		System.out.println(g.getPosActuelle().toString());

		g.deplacementBas();
		System.out.println(g.getPosActuelle().toString()); 

		g.deplacementBas();
		System.out.println(g.getPosActuelle().toString());
	}
}
