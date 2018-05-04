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

		Individu test = null;
		
		// Test 1
		Population population = new Population(map,graphe);
		
		for (int i=0 ; i<100; i++) {
			System.out.println("Generation n°"+population.getNombreGeneration());
			population.reinitialisation(map, graphe);
			population.lancerJeux();
			
			test = population.meilleurIndividu();
			
			population.lancerSelection();
			
			System.out.println();
		}
		
		
		Arbre arbre = test.getArbre();
		Individu individu = new Individu(map,graphe,arbre,80);
		new LancerPacMan(individu);
		
		for (int indexGeneration=0 ; indexGeneration<100; indexGeneration++) {
			int moyenne = 0;
			for (int indexIndividu=0 ; indexIndividu<100; indexIndividu++) {
				moyenne += population.getResultat(indexGeneration, indexIndividu);
			}
			System.out.println("Generation n°"+indexGeneration+": "+moyenne/100);
		}
		
		for (int indexIndividu=0 ; indexIndividu<100; indexIndividu++) {
			if(indexIndividu%10==0) {
				System.out.println("");
			}
			System.out.print(population.getResultat(99, indexIndividu)+" / ");
		}
	}
}