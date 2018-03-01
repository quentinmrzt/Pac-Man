package model;

import java.util.ArrayList;
import java.util.Observable;
//import java.util.Scanner;

public class Modelisation extends Observable {
	// Donnée du model
	public final static int PACMAN=0;
	public final static int BLINKY=1;
	public final static int PINKY=2;
	public final static int INKY=3;
	public final static int CLYDE=4; 	

	private Map map;
	private ArrayList<Personnage> personnages;
	private Graphe graphe;
	private int score;
	
	

	public Modelisation() {
		super();
		
		map = new Map("src/map_gomme.txt");
		graphe = new Graphe(map);
		
		int pacManX = map.getSpawnPacManX();
		int pacManY = map.getSpawnPacManY();
		Branche branchePacMan = graphe.getBranche(pacManX, pacManY);
		
		int fantomeX = map.getSpawnFantomeX();
		int fantomeY = map.getSpawnFantomeY();
		Branche brancheFantome = graphe.getBranche(fantomeX, fantomeY);
	
		personnages = new ArrayList<Personnage>();
		personnages.add(new PacMan(pacManX,pacManY,branchePacMan));
		personnages.add(new Blinky(fantomeX,fantomeY,brancheFantome,personnages.get(PACMAN)));
		personnages.add(new Pinky(fantomeX,fantomeY,brancheFantome));
		personnages.add(new Inky(fantomeX,fantomeY,brancheFantome));
		personnages.add(new Clyde(fantomeX,fantomeY,brancheFantome));
		
		score = 0;
	}

	public void directionPersonnage(int direction, int perso) {		
		if(direction==Personnage.HAUT) {
			personnages.get(perso).directionHaut();
		} else if(direction==Personnage.DROITE) {
			personnages.get(perso).directionDroite();
		} else if(direction==Personnage.BAS) {
			personnages.get(perso).directionBas();
		} else if(direction==Personnage.GAUCHE) {
			personnages.get(perso).directionGauche();
		}
	}
	
	public Personnage getPersonnage(int index) {
		return personnages.get(index);
	}

	// Orientation de pacMan à chaque noeud
	public void destinationPersonnages() {
		for (Personnage p: personnages) {
			p.destinationBranche();
		}
	}
	
	// Deplacement d'une case de chaque personnage
	public void deplacementPersonnages() {
		//personnages.get(0).deplacement();
		for (Personnage p: personnages) {
			p.deplacement();
		}
	}
	
	public void trouverCheminBlinky() {
		personnages.get(BLINKY).trouverChemin();
	}

	// Manger les pacGomme
	public void manger() {
		Personnage pacMan =  personnages.get(0);
		int x = pacMan.getPositionX();
		int y = pacMan.getPositionY();
		int type = map.getCase(x, y);

		if (type==Map.GOMME) {
			map.setCase(x, y, Map.SOL);
			map.mangerGomme();
			setScoreGomme();
		} else if (type==Map.SUPERGOMME) {
			map.setCase(x, y, Map.SOL);
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
	public PacMan getPM() {
		return (PacMan) personnages.get(0);
	}

	// ----------------------------------------
	// Setteur
	public void setScoreGomme() {
		score = score+10;
		
		setChanged();
		notifyObservers("G");
	}
	public void setScoreSuperGomme() {
		score = score+50;
		
		setChanged();
		notifyObservers("SP");
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