package model;

import java.io.*;
import java.util.Observable;


public class Map extends Observable {
	public final static int MUR = 0;
	public final static int SOL = 1;
	public final static int GOMME = 2;
	public final static int SUPERGOMME = 3;
	public final static int SPAWNPACMAN = 7;
	public final static int SPAWNFANTOME = 8;
	public final static int PRISON = 9;

	private int map[][];
	private int tailleX;
	private int tailleY;
	private int spawnPacManX, spawnPacManY;
	private int spawnFantomeX, spawnFantomeY;
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
				// On regarde la première ligne
				String lecture =  tmp.readLine();

				if (lecture != null) {
					int x = 0;
					while(x<lecture.length()) {
						// puis on récupère caractère par caractère
						String c = lecture.charAt(x)+"";
						// et on le convertit en entier
						map[x][y] = Integer.parseInt(c);
						// On garde en mémoire cb il y a de gomme
						if (map[x][y]==GOMME) {
							nbGomme++;
						} else if (map[x][y]==SUPERGOMME) {
							nbSuperGomme++;
						} else if (map[x][y]==SPAWNPACMAN) {
							spawnPacManX = x;
							spawnPacManY = y;
							map[x][y] = SOL;
						} else if (map[x][y]==SPAWNFANTOME) {
							spawnFantomeX = x;
							spawnFantomeY = y;
							map[x][y] = SOL;
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
				System.out.print(map[x][y]+" ");
			}
			System.out.println("");
		}
	}

	// GETTEUR
	public int getCase(int x, int y) {
		if (x>=tailleX || y>=tailleY) {
			System.out.println("Aie, x:"+x+" ou y:"+y+" est hors limite.");
			return 99999;
		}
		
		if (x<0 || y<0) {
			System.out.println("Aie, x:"+x+" ou y:"+y+" est hors limite.");
			return -99999;
		}
		
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
	public int getSpawnPacManX() {
		return spawnPacManX;
	}
	public int getSpawnPacManY() {
		return spawnPacManY;
	}
	public int getSpawnFantomeX() {
		return spawnFantomeX;
	}
	public int getSpawnFantomeY() {
		return spawnFantomeY;
	}

	// SETTEUR
	public void setCase(int x, int y, int type) {
		// La seule modification autorisée est la consommation de pacGomme
		if (type==SOL && (map[x][y]==SUPERGOMME || map[x][y]==GOMME)) {
			map[x][y] = type;

			setChanged();
			notifyObservers(map);
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
		
		if (nbGomme==0) {
			setChanged();
			notifyObservers("Fin du jeu.");
		}
	}
	public void mangerSuperGomme(int x, int y) {
		this.setCase(x, y, SOL);
		
		if (nbSuperGomme>0) {
			nbSuperGomme--;
		} else {
			System.err.println("ERREUR: Trop de super gomme on été mangée.");
		}
		
		if (nbSuperGomme==0) {
			setChanged();
			notifyObservers("Fin de l'invulnérabilité.");
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
		// map en simplifié
		for (int y=0 ; y<tailleY ; y++) {			
			for (int x=0 ; x<tailleX ; x++) {
				int type = getCase(x,y);
				if (type == MUR) {
					map[x][y] = MUR;
				} else if (type == SOL || type == GOMME || type == SUPERGOMME || type == SPAWNPACMAN || type == SPAWNFANTOME) {
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

