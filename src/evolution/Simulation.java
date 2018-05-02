package evolution;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Simulation implements Runnable {
	private boolean executionSimulation;
	private Population population;

	public Simulation(Population population) {
		this.population = population;
		this.executionSimulation = true;
		this.lancerSimulation();

		// Notre executor mono-thread
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(this);
		service.shutdown();
	}

	public boolean estEnSimulation() { return executionSimulation; }

	private void lancerSimulation() {
		ExecutorService execute = Executors.newFixedThreadPool(population.getNombrePopulation());

		// On exécute chaque "Runnable" de la liste "runnables"
		int nbPopulation = population.getNombrePopulation();
		for (int i=0 ; i<nbPopulation ; i++) {
			execute.execute((Runnable) population.getIndividu(i));
		}

		this.executionSimulation = true;
		// On ferme l'executor une fois les taches finies
		// En effet shutdown va attendre la fin d'exécution des tâches
		execute.shutdown();
	}

	public void run() {
		int nbPopulation = population.getNombrePopulation();
		boolean fin = false;

		// Vérifie que quelqu'un joue
		while (!fin) {
			fin = true;
			for (int i=0 ; i<nbPopulation ; i++) {
				if(population.getIndividu(i).estEnJeu()) {
					fin = false;
				}
			}
		}
		
		this.executionSimulation = false;
	}
}