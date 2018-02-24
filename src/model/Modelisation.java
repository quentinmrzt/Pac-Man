package model;

import java.util.ArrayList;
import java.util.Observable;
//import java.util.Scanner;

public class Modelisation extends Observable {
	// Donnée du model
	private Map map;
	private ArrayList<Personnage> personnages;
	private Graphe graphe;
	private int score;

	public Modelisation() {
		map = new Map("src/map_gomme.txt");
		graphe = new Graphe(map);

		personnages = new ArrayList<Personnage>();
		personnages.add(new PacMan(graphe.getNoeud(map.getSpawnPacManX(), map.getSpawnPacManY())));
		personnages.add(new Blinky(graphe.getNoeud(map.getSpawnFantomeX(), map.getSpawnFantomeY())));
		personnages.add(new Pinky(graphe.getNoeud(map.getSpawnFantomeX(), map.getSpawnFantomeY())));
		personnages.add(new Inky(graphe.getNoeud(map.getSpawnFantomeX(), map.getSpawnFantomeY())));
		personnages.add(new Clyde(graphe.getNoeud(map.getSpawnFantomeX(), map.getSpawnFantomeY())));

		score = 0;
	}

	public void directionPersonnage(int direction, int perso) {		
		if(direction==0) {
			personnages.get(perso).directionHaut();
		} else if(direction==1) {
			personnages.get(perso).directionDroite();
		} else if(direction==2) {
			personnages.get(perso).directionBas();
		} else if(direction==3) {
			personnages.get(perso).directionGauche();
		}
	}

	// Orientation de pacMan à chaque noeud
	public void destinationPersonnages() {
		//personnages.get(0).destination();
		for (Personnage p: personnages) {
			p.destination();
		}
	}
	
	// Deplacement d'une case de chaque personnage
	public void deplacementPersonnages() {
		//personnages.get(0).deplacement();
		for (Personnage p: personnages) {
			p.deplacement();
		}
	}

	// Manger les pacGomme
	/*public void mangerPacGomme() {
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
	}*/

	// ----------------------------------------
	// Getteur
	public Map getMap() {
		return map;
	}
	public int getScore() {
		return score;
	}
	public PacMan getPM() {
		return (PacMan) personnages.get(0);
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