package jeu;

import java.util.ArrayList;
import java.util.List;

import arbre.Arbre;
import evolution.Population;
import graphe.Graphe;
import model.Map;
import viewArbre.FenetreArbres;

public class LancerEvolution {
	public static void main(String[] args) {	
		int NOMBREGENERATION = 100;
		int PROFONDEUR = 10;
		int NOMBREPOPULATION = 100;
		int NBSELECTION = 80;
		int NBVAINQUEUR = 4;
		double POURCENTAGEMUTATION = 0.20;

		Map map = new Map("src/map_gomme.txt");
		Graphe graphe = new Graphe(map);

		Population population = new Population(map,graphe,PROFONDEUR,NOMBREPOPULATION,NBSELECTION,NBVAINQUEUR,POURCENTAGEMUTATION);

		for (int indexGeneration=0 ; indexGeneration<NOMBREGENERATION; indexGeneration++) {
			population.lancerJeux();
			population.lancerSelection();
			population.reinitialisation(map, graphe);
		}

		List<Arbre> arbres = new ArrayList<Arbre>();

		for (int i=0 ; i<100 ; i++) {
			arbres.add(population.getIndividu(i).getArbre());
		}

		new FenetreArbres(arbres);

		for (int generation=0 ; generation<100 ; generation++) {
			for (int individu=0 ; individu<100 ; individu++) {
				if(population.getInformation(generation, individu).estVainqueur()) {
					System.out.print(population.getInformation(generation, individu).getScore()+" / ");
				}
			}
			System.out.println("");
		}
	}
}