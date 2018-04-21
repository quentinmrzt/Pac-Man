package jeu;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import controller.Controller;
import graphe.Graphe;
import model.Map;
import model.Modelisation;
import view.Fenetre;

public class LancerPacMan {
	public static void main(String[] args) {
		Map map = new Map("src/map_gomme.txt");
		Graphe graphe = new Graphe(map);

		// Notre model
		Modelisation modelisation = new Jeu(map,graphe);

		//Notre executor mono-thread
		ExecutorService execute = Executors.newSingleThreadExecutor();
		//La méthode qui se charge de l'exécution des tâches
		executeRunnables(execute, modelisation);

		// Notre controler
		Controller control = new Controller(modelisation.getMonde());
		// Avec ça: on a une fenetre avec un menu
		Fenetre fenetre = new Fenetre(control,modelisation.getMonde());
		// La fenêtre devient observeur du model
		modelisation.getMonde().addObserver(fenetre);
	}

	public static void executeRunnables(final ExecutorService service, Modelisation modelisation){
		// On exécute chaque "Runnable" de la liste "runnables"
		service.execute(modelisation);

		// On ferme l'executor une fois les taches finies
		// En effet shutdown va attendre la fin d'exécution des tâches
		service.shutdown();
	}
}