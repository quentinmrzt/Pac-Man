package evolution;

import java.util.ArrayList;
import java.util.List;

import arbre.Arbre;
import arbre.Noeud;
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
				System.out.println("Model n°"+index+" avec un score de "+population.get(index).getScore()+" points.");
				Arbre arbre = population.get(index).getArbre();
				arbre.affiche();
				return population.get(index);
			} else {
				System.err.println("Erreur: la population est vide.");
				return null;
			}
		} else {
			System.out.println("La simulation n'est pas terminée.");
			return null;
		}
	}

	public List<Individu> tournoi(int selection, int nbVainqueur) {
		List<Individu> tmp = new ArrayList<Individu>(population);
		List<Individu> participant = new ArrayList<Individu>();
		List<Individu> vainqueur = new ArrayList<Individu>();

		for (int i=0 ; i<selection ; i++) {
			int random = (int) (Math.random() * tmp.size());

			participant.add(tmp.get(random));
			tmp.remove(random);
		}


		// On choisis les meilleurs de la selection
		for (int i=0 ; i<nbVainqueur ; i++) {
			int max = -1;
			Individu leMeilleur = null;
			for(Individu m:participant) {
				if (m.getScore()>max) {
					max = m.getScore();
					leMeilleur = m;
				}
			}

			if (leMeilleur!=null) {
				vainqueur.add(leMeilleur);
				participant.remove(leMeilleur);
			}
		}

		return vainqueur;
	}

	public List<Individu> croisement(Map map, Graphe graphe, List<Individu> pop) {
		List<Individu> nouvelleGeneration = new ArrayList<Individu>();

		/* **************************** */
		// Sauvegarde des anciens
		for(Individu i: pop) {
			Individu copie = i.clone();
			nouvelleGeneration.add(copie);
		}
		
		
		/* **************************** */
		// INDIVIDU 1
		Individu individuP1 = pop.get(0);
		
		int profondeur = individuP1.getArbre().getProfondeur();
		int profondeurAleatoire = (int) (1 + (Math.random() * (profondeur-1))); // en 1 et la profondeur
		
		int nombreNoeud = individuP1.getArbre().nbNoeud(profondeurAleatoire);
		int noeudAleatoire = (int) (Math.random() * nombreNoeud);

		Noeud noeudP1 = individuP1.getArbre().getNoeud(profondeurAleatoire, noeudAleatoire);
		
		/* **************************** */
		// INDIVIDU 2
		
		Individu individuP2 = pop.get(1);
		nombreNoeud = individuP2.getArbre().nbNoeud(profondeurAleatoire);
		noeudAleatoire = (int) (Math.random() * nombreNoeud);
		Noeud noeudP2 = individuP2.getArbre().getNoeud(profondeurAleatoire, noeudAleatoire);
		
		/* **************************** */
		// INVERSION
		Noeud pere1 = noeudP1.getPere();
		Noeud pere2 = noeudP2.getPere();
		// Changement de pere
		noeudP1.addPere(pere2);
		noeudP2.addPere(pere1);
		// Changement de fils
		pere1.changeFils(noeudP1, noeudP2);
		pere2.changeFils(noeudP2, noeudP1);

		// On ajoute à la nouvelle génération
		nouvelleGeneration.add(individuP1);
		nouvelleGeneration.add(individuP2);
		
		return nouvelleGeneration;
	}
}
