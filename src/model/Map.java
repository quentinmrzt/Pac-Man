package model;

import java.io.*;


public class Map {
	final int MUR = 0;
	final int SOL = 1;
	
	final int GOMME = 2;
	final int SUPERGOMME = 3;

	
	protected int mapIni[][];
	protected int map[][];
	protected int tailleX;
	protected int tailleY;

	public Map() {
		tailleX = 28;
		tailleY = 31;
		mapIni = new int[tailleX][tailleY];
		this.ouvrirMap();
		map = mapIni;
		//this.afficherMap();
	}

	public void ouvrirMap() {
		//Ouverture du Fichier 
		try {
			boolean fin = false;
			BufferedReader tmp = new BufferedReader(new FileReader("C:\\Users\\Quent\\eclipse-workspace\\Pac-Man\\src\\map.txt"));
			int y = 0;
			while(!fin) {
				String lecture =  tmp.readLine();
				if (lecture != null) {
					int x = 0;
					while(x<lecture.length()) {
						String c = lecture.charAt(x)+"";
						mapIni[x][y] = Integer.parseInt(c);
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
				System.out.print(mapIni[x][y]+" ");
			}
			System.out.println("");
		}
	}

	public int getCaseIni(int x, int y) {
		return mapIni[x][y];
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
		if(y>0 && getCaseIni(x,y-1)==SOL) {
			haut=true;
		}
		// a Droite
		if(x<tailleX-1 && getCaseIni(x+1,y)==SOL) {
			droite=true;
		}
		// en Bas
		if(y<tailleY-1 && getCaseIni(x,y+1)==SOL) {
			bas=true;
		}
		// a Gauche
		if(x>0 && getCaseIni(x-1,y)==SOL) {
			gauche=true;
		}
		
		return (haut || bas) && (droite || gauche);
	}
}

