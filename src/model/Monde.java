package model;

import java.util.Scanner;

public class Monde {

	protected Map map;
	protected PacMan pacMan;
	protected Graphe graphe;

	public Monde() {
		map = new Map();
		graphe = new Graphe(map);
		pacMan = new PacMan();
	}

	public static void main(String[] args) {
		Monde monde = new Monde();
		
		Scanner s = new Scanner(System.in);
		String str = "";

		boolean fin = false;
		while (!fin) {
			System.out.println(monde.graphe.getPosActuelle().toString());
			
			System.out.print("Haut (h) / Droite (d) / Bas (b) / Gauche (g) / Fin (f) = ");	   
			str = s.next();
			
			if(str.equals("h")) {
				if(!monde.graphe.deplacementHaut()) {
					System.out.println("Impossible.");
				}
			} else if (str.equals("d")) {
				if(!monde.graphe.deplacementDroite()) {
					System.out.println("Impossible.");
				}
			} else if (str.equals("b")) {
				if(!monde.graphe.deplacementBas()) {
					System.out.println("Impossible.");
				}
			} else if (str.equals("g")) {
				if(!monde.graphe.deplacementGauche()) {
					System.out.println("Impossible.");
				}
			} else if (str.equals("f")) {
				System.out.println("Bye.");
				fin = true;
			} else {
				System.out.println("ERREUR DE FRAPPE !");
			}
		}
		
		s.close();
	}
}
