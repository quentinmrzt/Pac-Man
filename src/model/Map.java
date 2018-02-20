package model;

import java.io.*;
import java.util.Observable;


public class Map extends Observable {
	final int MUR = 0;
	final int SOL = 1;
	final int GOMME = 2;
	final int SUPERGOMME = 3;
	final int PRISON = 9;


	private int map[][];
	private int tailleX;
	private int tailleY;
	private int nbGomme;
	private int nbSuperGomme;

	public Map(String chemin) {
		tailleX = 28;
		tailleY = 31;
		nbGomme = 0;
		nbSuperGomme = 0;
		map = new int[tailleX][tailleY];
		this.ouvrirMap(chemin);
	}

	public Map(Map m) {
		tailleX = m.getTailleX();
		tailleY = m.getTailleY();
		map = new int[tailleX][tailleY];

		for (int y=0 ; y<tailleY ; y++) {
			for (int x=0 ; x<tailleX ; x++) {
				map[x][y] = m.getCase(x, y);
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
				// On regarde la premi�re ligne
				String lecture =  tmp.readLine();

				if (lecture != null) {
					int x = 0;
					while(x<lecture.length()) {
						// puis on r�cup�re caract�re par caract�re
						String c = lecture.charAt(x)+"";
						// et on le convertit en entier
						map[x][y] = Integer.parseInt(c);
						// On garde en m�moire cb il y a de gomme
						if (map[x][y]==GOMME) {
							nbGomme++;
						} else if (map[x][y]==SUPERGOMME) {
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
			System.out.println("Fichier Non Trouv� !!" ); 
		} catch (IOException ex) { 
			System.out.println("Erreur lecture ligne fichier !!" ); 
		} 
	}

	public void afficherMap() {
		for (int y=0 ; y<this.tailleY ; y++) {
			for(int x=0 ; x<this.tailleX ; x++) {
				System.out.print(map[x][y]+" ");
			}
			System.out.println("");
		}
	}

	// GETTEUR
	public int getCase(int x, int y) {
		return map[x][y];
	}
	public int getTailleX() {
		return tailleX;
	}
	public int getTailleY() {
		return tailleY;
	}
	public int getNbGomme() {
		return nbGomme;
	}
	public int getNbSuperGomme() {
		return nbSuperGomme;
	}

	// SETTEUR
	public void setCase(int x, int y, int type) {
		// La seule modification autoris�e est la consommation de pacGomme
		if (type==SOL && (map[x][y]==SUPERGOMME || map[x][y]==GOMME)) {
			map[x][y] = type;

			setChanged();
			notifyObservers(map);
		} else {
			System.err.println("ERREUR: Initialisation d'une case interdite dans la Map.");
		}
	}
	public void mangerGomme() {
		if (nbGomme>0) {
			nbGomme--;
		} else {
			System.err.println("ERREUR: Trop de gomme on �t� mang�e.");
		}
		
		if (nbGomme==0) {
			setChanged();
			notifyObservers("Fin du jeu.");
		}
	}
	public void mangerSuperGomme() {
		if (nbSuperGomme>0) {
			nbSuperGomme--;
		} else {
			System.err.println("ERREUR: Trop de super gomme on �t� mang�e.");
		}
		
		if (nbSuperGomme==0) {
			setChanged();
			notifyObservers("Fin de l'invuln�rabilit�.");
		}
	}

	public boolean isIntersection(int x, int y) {
		boolean haut=false;
		boolean droite=false;
		boolean bas=false;
		boolean gauche=false;

		// en Haut
		if(y>0 && getCase(x,y-1)==SOL) {
			haut=true;
		}
		// a Droite
		if(x<tailleX-1 && getCase(x+1,y)==SOL) {
			droite=true;
		}
		// en Bas
		if(y<tailleY-1 && getCase(x,y+1)==SOL) {
			bas=true;
		}
		// a Gauche
		if(x>0 && getCase(x-1,y)==SOL) {
			gauche=true;
		}

		return (haut || bas) && (droite || gauche);
	}
	public void simplification() {
		// map en simplifi�
		for (int y=0 ; y<tailleY ; y++) {			
			for (int x=0 ; x<tailleX ; x++) {
				if (getCase(x, y) == MUR) {
					map[x][y] = MUR;
				} else if (getCase(x, y) == SOL || getCase(x, y) == GOMME || getCase(x, y) == SUPERGOMME) {
					map[x][y] = SOL;
				} else if (getCase(x, y) == PRISON) {
					map[x][y] = PRISON;
				} else { 
					System.err.println("Un chiffre n'est reconnu en x:"+x+" y:"+y+".");
					System.exit(0);
				}
			}
		}		
	}
}

