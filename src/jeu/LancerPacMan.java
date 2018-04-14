package jeu;

import java.util.ArrayList;

//import controller.Controller;
import graphe.Graphe;
import model.Map;
import model.Modelisation;
//import view.Fenetre;

public class LancerPacMan {
	public static void main(String[] args) {
		Map map = new Map("src/map_gomme.txt");
		Graphe graphe = new Graphe(map);

		ArrayList<Modelisation> population = new ArrayList<Modelisation>();

		// Notre model
		for(int nb=0 ; nb<100 ; nb++) {
			population.add(new Modelisation(map, graphe));
		}

		boolean fin = false;
		while (!fin) {
			for(Modelisation m:population) {
				fin = true;
				if(m.estEnJeu()) {
					fin = false;
				} 
			}
		}

		// Tout est fini
		int max = -1;
		int i = 0;
		int index = -1;
		for(Modelisation m:population) {
			System.out.println("Score: "+m.getScore());
			if (m.getScore()>max) {
				max = m.getScore();
				index = i;
			}
			i++;
		}

		if(index!=-1) {
			population.get(index).getArbre().affiche(population.get(index).getArbre().getNoeud(), 0);
		}

		// Notre controler
		//Controller control = new Controller(model);

		// Avec ça: on a une fenetre avec un menu
		//Fenetre fenetre = new Fenetre(control,model);

		// La fenêtre devient observeur du model
		//model.addObserver(fenetre);
	}
}