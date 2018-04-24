package jeu;

import java.util.List;

import evolution.Individu;
import evolution.Population;
import graphe.Graphe;
import model.Map;

public class LancerEvolution {
	public static void main(String[] args) {
		Map map = new Map("src/map_gomme.txt");
		Graphe graphe = new Graphe(map);
		
		Population population = new Population(map,graphe);
		
		while (population.estEnSimulation()) {
			// On attend
		}
		System.out.println("Fin de la simulation");
		
		int nbParticipant = 100;
		int nbVainqueur = 2;
		List<Individu> vainqueur = population.tournoi(nbParticipant, nbVainqueur);
		
		for (Individu i: vainqueur) {
			System.out.println("Score du vainqueur: "+i.getScore());
		}
		
		List<Individu> croisement = population.croisement(map, graphe, vainqueur);
		
		System.out.println("--------------------------------");
		for (Individu i: croisement) {
			System.out.println("Score: "+i.getScore());
			i.getArbre().affiche();
			System.out.println();
		}
	}
}