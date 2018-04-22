package jeu;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import graphe.Graphe;
import model.Map;
import model.Modelisation;
import view.Fenetre;

public class LancerPacMan {
	
	public LancerPacMan(Modelisation modelisation) {
		Fenetre fenetre = new Fenetre(modelisation.getMonde());
		// La fenêtre devient observeur du model
		modelisation.getMonde().addObserver(fenetre);
		
		//Notre executor mono-thread
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		//La méthode qui se charge de l'exécution des tâches
		service.execute(modelisation);
		
		service.shutdown();
	}
	
	public static void main(String[] args) {
		Map map = new Map("src/map_gomme.txt");
		Graphe graphe = new Graphe(map);

		// Notre model
		new LancerPacMan(new Jeu(map,graphe));
	}
}