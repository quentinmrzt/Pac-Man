package jeu;

import java.util.List;

import arbre.Noeud;
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

		
		// -------------------------------------------------------------------------------
		
		{
			// Individu 0: Copie, premier parent
			Noeud test = croisement.get(0).getArbre().getNoeud();
			Noeud fils = test.getDroite();
			Noeud pere = fils.getPere();
			System.out.print("Individu 0:    Copie,  premier parent: ");

			if(pere==null) {
				System.out.println("Ce projet me saoul.");
			} else {
				System.out.println("Bravo mec, tu serais presque bon.");
			}
		}

		{
			// Individu 0: Copie, deuxième parent
			Noeud test = croisement.get(0).getArbre().getNoeud();
			Noeud fils = test.getDroite().getDroite();
			Noeud pere = fils.getPere();
			System.out.print("Individu 0:    Copie, deuxième parent: ");
			
			if(pere==null) {
				System.out.println("Ce projet me saoul.");
			} else {
				System.out.println("Bravo mec, tu serais presque bon.");
			}
		}

		{
			// Individu 2: Original, premier parent
			Noeud test = croisement.get(2).getArbre().getNoeud();
			Noeud fils = test.getDroite();
			Noeud pere = fils.getPere();
			System.out.print("Individu 2: Original,  premier parent: ");

			if(pere==null) {
				System.out.println("Ce projet me saoul.");
			} else {
				System.out.println("Bravo mec, tu serais presque bon.");
			}
		}

		{
			// Individu 2: Original, deuxième parent
			Noeud test = croisement.get(2).getArbre().getNoeud();
			Noeud fils = test.getDroite().getDroite();
			Noeud pere = fils.getPere();
			System.out.print("Individu 2: Original, deuxième parent: ");

			if(pere==null) {
				System.out.println("Ce projet me saoul.");
			} else {
				System.out.println("Bravo mec, tu serais presque bon.");
			}
		}
	}
}