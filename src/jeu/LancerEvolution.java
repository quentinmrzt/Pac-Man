package jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import arbre.Arbre;
import evolution.Individu;
import graphe.Graphe;
import model.Map;

public class LancerEvolution {
	public static void main(String[] args) {
		int nombrePopulation = 100;
		
		Map map = new Map("src/map_gomme.txt");
		Graphe graphe = new Graphe(map);

		List<Individu> population = new ArrayList<Individu>();

		//Cette fois on créer un pool de 10 threads maximum
		ExecutorService execute = Executors.newFixedThreadPool(nombrePopulation);

		// Notre model
		for(int nb=0 ; nb<nombrePopulation ; nb++) {
			population.add(new Individu(map, graphe));
		}

		// On exécute chaque "Runnable" de la liste "runnables"
		for(Runnable r : population){
			execute.execute(r);
		}
		
		boolean fin = false;
		while (!fin) {
			fin = true;
			for(Individu m:population) {
				if(m.estEnJeu()) {
					fin = false;
				}
			}
		}


		// Tout est fini
		int max = -1;
		int i = 0;
		int index = -1;
		for(Individu m:population) {
			//System.out.println("Score: "+m.getScore());
			if (m.getScore()>max) {
				max = m.getScore();
				index = i;
			}
			i++;
		}

		if(index!=-1) {
			System.out.println("Model n°"+index+" avec un score de "+population.get(index).getScore()+" points.");
			Arbre arbre = population.get(index).getArbre();
			arbre.affiche();
		}
		
		// On ferme l'executor une fois les taches finies
		// En effet shutdown va attendre la fin d'exécution des tâches
		execute.shutdown();
	}
}
