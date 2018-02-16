package model;

import java.io.*;


public class Map {
	final int MUR = 0;
	final int SOL = 1;
	final int GOMME = 2;
	final int SUPERGOMME = 3;
	final int PRISON = 9;


	private int map[][];
	private int tailleX;
	private int tailleY;

	public Map(String chemin) {
		tailleX = 28;
		tailleY = 31;
		map = new int[tailleX][tailleY];
		this.ouvrirMap(chemin);
	}
	
	public Map(Map map) {
		tailleX = map.getTailleX();
		tailleY = map.getTailleY();
		this.map = new int[tailleX][tailleY];
		
		for (int y=0 ; y<tailleY ; y++) {
			for (int x=0 ; x<tailleX ; x++) {
				this.map[x][y] = map.getCase(x, y);
			}
		}
	}

	public void ouvrirMap(String chemin) {
		//Ouverture du Fichier 
		try {
			boolean fin = false;
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

	public int getCase(int x, int y) {
		return map[x][y];
	}
	public int getTailleX() {
		return tailleX;
	}
	public int getTailleY() {
		return tailleY;
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

