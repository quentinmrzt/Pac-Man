package jeu;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import controller.Controller;
import graphe.Graphe;
import model.Map;
import model.Modelisation;
import view.Fenetre;

public class LancerPacMan {
	
	public LancerPacMan(Modelisation modelisation) {
		// Notre controler
		Controller control = new Controller(modelisation.getMonde());
		// Avec �a: on a une fenetre avec un menu
		Fenetre fenetre = new Fenetre(control,modelisation.getMonde());
		// La fen�tre devient observeur du model
		modelisation.getMonde().addObserver(fenetre);
		
		//Notre executor mono-thread
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		//La m�thode qui se charge de l'ex�cution des t�ches
		service.execute(modelisation);
		
		//service.shutdown();
	}
	
	public static void main(String[] args) {
		Map map = new Map("src/map_gomme.txt");
		Graphe graphe = new Graphe(map);

		// Notre model
		new LancerPacMan(new Jeu(map,graphe));
	}
}