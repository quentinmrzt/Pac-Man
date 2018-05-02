package jeu;

import arbre.Arbre;
import evolution.Individu;
import evolution.Population;
import graphe.Graphe;
import model.Map;

public class LancerEvolution {
	public static void main(String[] args) {
		Map map = new Map("src/map_gomme.txt");
		Graphe graphe = new Graphe(map);

		Population population = new Population(map,graphe);
		
		for (int i=0 ; i<100 ; i++) {
			System.out.println("Generation n°"+population.getNombreGeneration());
			population.lancerSelection();
			population.reinitialisation(map, graphe);
			
			//System.out.println();
		}
		
		Individu i = population.meilleurIndividu();
		Arbre arbre = i.getArbre();
		Individu individu = new Individu(map,graphe,arbre,80);
		new LancerPacMan(individu);
	}
}