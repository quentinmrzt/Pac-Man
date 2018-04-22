package evolution;

import java.util.ArrayList;
import java.util.List;

import arbre.Arbre;
import graphe.Graphe;
import model.Map;

public class Population {
	final static private int NOMBREPOPULATION = 100;
	
	private List<Individu> population;
	private Simulation simulation;
	
	public Population(Map map, Graphe graphe) {
		this.population = new ArrayList<Individu>();
		
		for(int nb=0 ; nb<NOMBREPOPULATION ; nb++) {
			population.add(new Individu(map, graphe));
		}
		
		this.simulation = new Simulation(this);
	}
	
	public boolean estEnSimulation() { return simulation.estEnSimulation(); }
	public int getNombrePopulation() { return NOMBREPOPULATION; }
	public Individu getIndividu(int index) { return population.get(index); }
	
	public Individu meilleurIndividu() {
		if(!this.estEnSimulation()) {
			// Tout est fini
			int max = -1;
			int i = 0;
			int index = -1;
			for(Individu m:population) {
				if (m.getScore()>max) {
					max = m.getScore();
					index = i;
				}
				i++;
			}

			if(index!=-1) {
				System.out.println("Model n�"+index+" avec un score de "+population.get(index).getScore()+" points.");
				Arbre arbre = population.get(index).getArbre();
				arbre.affiche();
				return population.get(index);
			}
		}
		
		return null;
	}
}
