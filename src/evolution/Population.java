package evolution;

import java.util.ArrayList;
import java.util.List;

import arbre.Noeud;
import graphe.Graphe;
import model.Map;

public class Population {
	final static private int NOMBREPOPULATION = 100;
	final static private int NBSELECTION = 100;
	final static private int NBVAINQUEUR = 2;

	private int nbIndividu, nbGeneration;
	private List<Individu> population;
	private List<Thread> liste;
	private boolean enSimulation;

	// Population initiale
	public Population(Map map, Graphe graphe) {
		this.nbIndividu = NOMBREPOPULATION;
		this.nbGeneration = 1;

		// On crée les individus
		this.population = new ArrayList<Individu>();
		for(int nb=0 ; nb<NOMBREPOPULATION ; nb++) {
			population.add(new Individu(map, graphe));
		}
	}

	public int getNombrePopulation() { return NOMBREPOPULATION; }
	public Individu getIndividu(int index) { return population.get(index); }
	public int getNombreIndividu() { return nbIndividu; }
	public int getNombreGeneration() { return nbGeneration; }
	public boolean estEnSimulation() { return enSimulation; }

	public void tournoi(int selection, int nbVainqueur) {
		int nbSupprimer;

		// On "tue" le surplus de population aléatoirement
		nbSupprimer = NOMBREPOPULATION-selection;

		for (int i=0 ; i<nbSupprimer ; i++) {
			int random = (int) (Math.random() * nbIndividu);

			population.remove(random);
			nbIndividu--;
		}

		// On "tue" les moins bons
		nbSupprimer = nbIndividu-nbVainqueur;

		for (int i=0 ; i<nbSupprimer ; i++) {
			int min = 999999;
			Individu leMoinsBon = null;
			for(Individu m: population) {
				if (m.getScore()<min) {
					min = m.getScore();
					leMoinsBon = m;
				}
			}

			if (leMoinsBon!=null) {
				population.remove(leMoinsBon);
				nbIndividu--;
			}
		}
	}

	public void croisement() {
		int nbACreer = (NOMBREPOPULATION-nbIndividu)/2;
		List<Individu> nouvelleGeneration = new ArrayList<Individu>();

		/* **************************** */
		// On copie les anciens pour travailler dessus
		for(Individu i: population) {
			Individu copie = i.clone();
			nouvelleGeneration.add(copie);
		}

		/* **************************** */
		for(int i=0 ; i<nbACreer ; i++) {			
			// INDIVIDU 1
			Individu individuP1 = nouvelleGeneration.get(0).clone();

			int profondeur = individuP1.getArbre().getProfondeur();
			int profondeurAleatoire = (int) (1 + (Math.random() * (profondeur-1))); // en 1 et la profondeur

			int nombreNoeud = individuP1.getArbre().nbNoeud(profondeurAleatoire);
			int noeudAleatoire = (int) (Math.random() * nombreNoeud);

			Noeud noeudP1 = individuP1.getArbre().getNoeud(profondeurAleatoire, noeudAleatoire);


			// INDIVIDU 2
			Individu individuP2 = nouvelleGeneration.get(1).clone();;
			nombreNoeud = individuP2.getArbre().nbNoeud(profondeurAleatoire);
			noeudAleatoire = (int) (Math.random() * nombreNoeud);
			Noeud noeudP2 = individuP2.getArbre().getNoeud(profondeurAleatoire, noeudAleatoire);


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
			population.add(individuP1);
			nbIndividu++;
			population.add(individuP2);
			nbIndividu++;
		}
	}

	public Individu meilleurIndividu() {
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
			//System.out.println("Model n°"+index+" avec un score de "+population.get(index).getScore()+" points.");
			//Arbre arbre = population.get(index).getArbre();
			//arbre.affiche();
			return population.get(index);
		} else {
			System.err.println("Erreur: la population est vide.");
			return null;
		}
	}

	public void informationThread() {
		int terminated=0, runnable=0, blocked=0, autre=0;

		for (Thread t: liste) {
			if (t.getState()==Thread.State.TERMINATED) {
				terminated++;
			} else if (t.getState()==Thread.State.RUNNABLE) {
				runnable++;
			} else if (t.getState()==Thread.State.BLOCKED) {
				blocked++;
			} else {
				autre++;
			}
		}

		System.out.println("Thread: NB="+liste.size()+" / En cours="+runnable+" / Terminé="+terminated+" / Blocké="+blocked+" / Autre="+autre);
	}

	public void lancerJeux() {
		// On lance l'execution
		for (int i=0 ; i<this.getNombreIndividu() ; i++) {
			liste.get(i).start();
		}
	}

	public void lancerSelection() {
		enSimulation = true;
		
		// On crée les threads
		this.liste = new ArrayList<Thread>();
		for (int i=0 ; i<NOMBREPOPULATION ; i++) {
			liste.add(new Thread(this.getIndividu(i)));
		}
		
		// On lance l'execution
		this.lancerJeux();

		// Vérifie que quelqu'un joue
		boolean fin = false;

		while (!fin) {
			fin = true;
			for(Thread t: liste) {
				if(t.getState()!=Thread.State.TERMINATED) {
					fin = false;
				}
			}
		}

		//System.out.println("Fin des jeux: NB="+nbIndividu);

		// Tout le monde à arrêté de jouer
		this.tournoi(NBSELECTION, NBVAINQUEUR);

		//System.out.println("Tournoi terminé: NB="+nbIndividu);

		this.croisement();

		//System.out.println("Croisement terminé: NB="+nbIndividu);
				
		enSimulation = false;
		nbGeneration++;
	}

	public void reinitialisation(Map map, Graphe graphe) {
		List<Individu> tmp = new ArrayList<Individu>();

		for(Individu i: population) {
			tmp.add(new Individu(map, graphe, i.getArbre(), 0));
		}
		
		population = tmp;
	}
}
