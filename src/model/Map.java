package model;

import java.io.*;


public class Map {
	protected int map[][];
	protected int tailleX;
	protected int tailleY;

	final int MUR = 0;
	final int SOL = 1;
	final int GOMME = 2;
	final int SUPERGOMME = 3;

	public Map() {
		tailleX = 28;
		tailleY = 31;
		map = new int[tailleX][tailleY];
		this.ouvrirMap();
		this.afficherMap();
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
						map[x][y] = Integer.parseInt(c);
						x++;
					}
				} else {
					fin = true;
				}
				y++;
			}
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

	public static void main (String[] args){
		new Map();
	}
} 

