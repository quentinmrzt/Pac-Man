package model;

import java.util.ArrayList;
import java.util.Observable;
//import java.util.Scanner;

public class Modelisation extends Observable {
	// ----------------------------------------
	// Donnée du model
	private Map map;
	private ArrayList<Personnage> personnages;
	private Graphe graphe;
	private int score;

	public Modelisation() {
		//int pacManX = 1;
		//int pacManY = 1;
		
		int pacManX = 14;
		int pacManY = 23;

		map = new Map("src/map_gomme.txt");
		graphe = new Graphe(map, pacManX, pacManY);
		
		personnages = new ArrayList<Personnage>();
		personnages.add(new PacMan());
		personnages.add(new Blinky());
		personnages.add(new Pinky());
		personnages.add(new Inky());
		personnages.add(new Clyde());
		
		score = 0;
	}


	

	public void deplacementPersonnages() {
		for (Personnage p: personnages) {
			p.deplacement();
		}
	}

	// Orientation de pacMan à chaque noeud
	public void destinationPersonnages() {
		for (Personnage p: personnages) {
			p.destination();
		}
	}

	// Manger les pacGomme
	public void mangerPacGomme() {
		int x = pacMan.getPositionX();
		int y = pacMan.getPositionY();
		int type = map.getCase(x, y);

		if (type==map.GOMME) {
			map.setCase(x, y, map.SOL);
			map.mangerGomme();
			setScoreGomme();
		} else if (type==map.SUPERGOMME) {
			map.setCase(x, y, map.SOL);
			map.mangerSuperGomme();
			setScoreSuperGomme();
		}
	}

	// ----------------------------------------
	// Getteur
	public Map getMap() {
		return map;
	}
	public int getScore() {
		return score;
	}

	// ----------------------------------------
	// Setteur
	public void setScoreGomme() {
		score = score+10;
	}
	public void setScoreSuperGomme() {
		score = score+50;
	}

	/*public static void main(String[] args) {
		Modelisation modelisation = new Modelisation();

		Scanner s = new Scanner(System.in);
		String str = "";

		boolean fin = false;
		while (!fin) {
			System.out.println(modelisation.graphe.getPosActuelle().toString());
			System.out.println(modelisation.pacMan.toString());

			System.out.print("Rien faire (r) / Haut (h) / Droite (d) / Bas (b) / Gauche (g) / Fin (f) = ");
			str = s.next();

			// 	CONTROLEUR
			if(str.equals("h")) {
				modelisation.deplacementPMHaut();
			} else if (str.equals("d")) {
				modelisation.deplacementPMDroite();
			} else if (str.equals("b")) {
				modelisation.deplacementPMBas();
			} else if (str.equals("g")) {
				modelisation.deplacementPMGauche();
			} else if (str.equals("f")) {
				System.out.println("Bye.");
				fin = true;
			} else if(str.equals("r")) {
				// Ne rien faire
			} else {
				System.out.println("ERREUR DE FRAPPE !");
			}

			System.out.println("Direction: "+modelisation.pacMan.getDirection()+". Prochaine: "+modelisation.pacMan.getProchaineDirection());

			// Permet l'orientation au noeud
			modelisation.destinationPacMan();
			// et on dit à pacMan d'y aller
			modelisation.deplacementPacMan();

			System.out.println(modelisation.pacMan.toString());
			System.out.println("");
		}

		s.close();
	}	*/

}