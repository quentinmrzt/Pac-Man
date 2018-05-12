package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Map {
	public final static int MUR = 0;
	public final static int SOL = 1;
	public final static int GOMME = 2;
	public final static int SUPERGOMME = 3;
	public final static int PRISON = 9;

	private int carte[][];
	private int tailleX;
	private int tailleY;

	private int nbGomme;
	private int nbSuperGomme;

	public Map(String chemin) {
		tailleX = 28;
		tailleY = 31;
		nbGomme = 0;
		nbSuperGomme = 0;
		carte = new int[tailleX][tailleY];
		this.ouvrirMap(chemin);
	}

	public Map(Map map) {
		this.tailleX = map.getTailleX();
		this.tailleY = map.getTailleY();
		this.nbGomme = map.getNbGomme();
		this.nbSuperGomme = map.getNbSuperGomme();

		this.carte = new int[this.tailleX][this.tailleY];

		for (int y=0 ; y<this.tailleY ; y++) {
			for(int x=0 ; x<this.tailleX ; x++) {
				this.carte[x][y] = map.getCase(x, y);
			}
		}
	}

	public void ouvrirMap(String chemin) {
		//Ouverture du Fichier 
		try {
			boolean fin = false;
			// On ouvre le fichier contenant la map
			BufferedReader tmp = new BufferedReader(new FileReader(chemin));
			int y = 0;
			while(!fin) {
				// On regarde la première ligne
				String lecture =  tmp.readLine();

				if (lecture != null) {
					int x = 0;
					while(x<lecture.length()) {
						// puis on récupère caractère par caractère
						String c = lecture.charAt(x)+"";
						// et on le convertit en entier
						carte[x][y] = Integer.parseInt(c);
						// On garde en mémoire cb il y a de gomme
						if (carte[x][y]==GOMME) {
							nbGomme++;
						} else if (carte[x][y]==SUPERGOMME) {
							nbSuperGomme++;
						} 
						x++;
					}
				} else {
					fin = true;
				}
				y++;
			}
			tmp.close();
		} catch (FileNotFoundException ex) { 
			System.out.println("Fichier Non Trouvé !!" ); 
		} catch (IOException ex) { 
			System.out.println("Erreur lecture ligne fichier !!" ); 
		} 
	}

	public void afficherMap() {
		for (int y=0 ; y<this.tailleY ; y++) {
			for(int x=0 ; x<this.tailleX ; x++) {
				System.out.print(carte[x][y]+" ");
			}
			System.out.println("");
		}
	}

	// GETTEUR
	public int getTailleX() {return tailleX;}
	public int getTailleY() {return tailleY;}

	public int getNbGomme() {return nbGomme;}
	public int getNbSuperGomme() {return nbSuperGomme;}

	public int getCase(int x, int y) {
		if (x>=tailleX || y>=tailleY) {
			System.out.println("ERREUR: x:"+x+" ou y:"+y+" est hors limite.");
			return 99999;
		}

		if (x<0 || y<0) {
			System.out.println("ERREUR: x:"+x+" ou y:"+y+" est hors limite.");
			return -99999;
		}

		return carte[x][y];
	}

	public boolean estGomme(int x, int y) {
		if (x>=0 && x<tailleX && y>=0 && y<tailleY) {
			if(this.getCase(x, y)==GOMME) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// SETTEUR
	public void setCase(int x, int y, int type) {
		// La seule modification autorisée est la consommation de pacGomme
		if (type==SOL && (carte[x][y]==SUPERGOMME || carte[x][y]==GOMME)) {
			carte[x][y] = type;
		} else {
			System.err.println("ERREUR: Initialisation d'une case interdite dans la Map.");
		}
	}
	public void mangerGomme(int x, int y) {
		this.setCase(x, y, SOL);

		if (nbGomme>0) {
			nbGomme--;
		} else {
			System.err.println("ERREUR: Trop de gomme on été mangée.");
		}
	}
	public void mangerSuperGomme(int x, int y) {
		this.setCase(x, y, SOL);

		if (nbSuperGomme>0) {
			nbSuperGomme--;
		} else {
			System.err.println("ERREUR: Trop de super gomme on été mangée.");
		}
	}

	public boolean isIntersection(int x, int y) {
		if(x<0 || y<0 || x>=tailleX || y>=tailleY) {
			System.err.println("Erreur: Test intersection hors borne");
			return false;
		}

		if(this.getCase(x,y)==MUR) {
			return false;
		}

		boolean haut=false;
		boolean droite=false;
		boolean bas=false;
		boolean gauche=false;

		// en Haut
		if(y>0 && this.getCase(x,y-1)!=MUR) {
			haut=true;
		}
		// a Droite
		if(x<tailleX-1 && this.getCase(x+1,y)!=MUR) {
			droite=true;
		}
		// en Bas
		if(y<tailleY-1 && this.getCase(x,y+1)!=MUR) {
			bas=true;
		}
		// a Gauche
		if(x>0 && this.getCase(x-1,y)!=MUR) {
			gauche=true;
		}

		return (haut || bas) && (droite || gauche);
	}
}

