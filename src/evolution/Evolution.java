package evolution;

import java.util.ArrayList;
import java.util.List;

import arbre.Arbre;
import graphe.Graphe;
import model.Map;

public class Evolution {
	private int nombreGeneration = 100;

	private int profondeur = 10;
	private int nombrePopulation = 100;
	private int nombreSelection = 80;
	private int nombreVainqueur = 4;
	private int pourcentageMutation = 20;

	private Population population;

	public Evolution() {
		Map map = new Map("src/map_gomme.txt");
		Graphe graphe = new Graphe(map);

		this.population = new Population(map, graphe, profondeur, nombrePopulation, nombreSelection, nombreVainqueur, pourcentageMutation/100);

		for (int indexGeneration=0 ; indexGeneration<nombreGeneration; indexGeneration++) {
			population.lancerJeux();
			population.lancerSelection();
			population.reinitialisation(map, graphe);
		}

		List<Arbre> arbres = new ArrayList<Arbre>();

		for (int i=0 ; i<100 ; i++) {
			arbres.add(population.getIndividu(i).getArbre());
		}

		//new FenetreArbres(arbres);
	}

	public int getNombreGeneration() { return nombreGeneration; }
	public int getProfondeur() { return profondeur; }
	public int getNombrePopulation() { return nombrePopulation; }
	public int getNombreSelection() { return nombreSelection; }
	public int getNombreVainqueur() { return nombreVainqueur; }
	public int getPourcentageMutation() { return pourcentageMutation; }
	
	public Information getInformation(int generation, int individu) { return population.getInformation(generation, individu); }
}
