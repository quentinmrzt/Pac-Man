package evolution;

import java.util.ArrayList;
import java.util.List;

import arbre.Arbre;
import arbre.Noeud;
import graphe.Graphe;
import model.Map;

public class Population {
	final static private int NOMBREPOPULATION = 100;
	final static private int NBSELECTION = 100;
	final static private int NBVAINQUEUR = 2;
	final static private double POURCENTAGECROISEMENT = 0.90;

	private int nbGeneration;
	private List<Individu> population, vainqueur;
	private List<Thread> liste;
	private boolean enSimulation;

	// Population initiale
	public Population(Map map, Graphe graphe) {
		this.nbGeneration = 1;

		// On crée les individus
		this.population = new ArrayList<Individu>();
		for(int nb=0 ; nb<NOMBREPOPULATION ; nb++) {
			population.add(new Individu(map, graphe));
		}
	}

	public int getNombrePopulation() { return NOMBREPOPULATION; }
	public Individu getIndividu(int index) { return population.get(index); }
	public int getNombreIndividu() { return population.size(); }
	public int getNombreGeneration() { return nbGeneration; }
	public boolean estEnSimulation() { return enSimulation; }

	public void tournoi() {
		int nbSupprimer;
		this.vainqueur = new ArrayList<Individu>();

		// On "tue" le surplus de population aléatoirement
		nbSupprimer = NOMBREPOPULATION-NBSELECTION;

		for (int i=0 ; i<nbSupprimer ; i++) {
			int random = (int) (Math.random() * this.getNombreIndividu());

			population.remove(random);
		}

		// On cherche les meilleurs
		for (int i=0 ; i<NBVAINQUEUR ; i++) {
			int max = -1;
			Individu leMeilleur = null;
			for(Individu m: population) {
				if (m.getScore()>max) {
					max = m.getScore();
					leMeilleur = m;
				}
			}

			// et ont les extraits
			if (leMeilleur!=null) {
				vainqueur.add(leMeilleur);
				
				population.remove(leMeilleur);
			}
		}
		
		// Puis on "tue" le reste
		population.clear();
	}

	public void croisement() {
		int tmp = (int) Math.floor((NOMBREPOPULATION-NBVAINQUEUR)*POURCENTAGECROISEMENT);
		int nbACreer = tmp/2;


		/* **************************** */
		for(int i=0 ; i<nbACreer ; i++) {			
			// INDIVIDU 1
			Individu individuP1 = vainqueur.get(0).clone();

			int profondeur = individuP1.getArbre().getProfondeur();
			int profondeurAleatoire = (int) (1 + (Math.random() * (profondeur-1))); // en 1 et la profondeur

			int nombreNoeud = individuP1.getArbre().nbNoeud(profondeurAleatoire);
			int noeudAleatoire = (int) (Math.random() * nombreNoeud);

			Noeud noeudP1 = individuP1.getArbre().getNoeud(profondeurAleatoire, noeudAleatoire);


			// INDIVIDU 2
			Individu individuP2 = vainqueur.get(1).clone();
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
			population.add(individuP2);
		}
	}
	
	public void mutation() {
		int nbACreer = NOMBREPOPULATION-this.getNombreIndividu()-NBVAINQUEUR;
		
		for(int i=0 ; i<nbACreer ; i++) {
			int aleatoire = (int) (Math.random() * NBVAINQUEUR);
			Individu individu = this.getIndividu(aleatoire).clone();
			
			int profondeur = individu.getArbre().getProfondeur();
			int profondeurAleatoire = (int) (1 + (Math.random() * (profondeur-1))); // en 1 et la profondeur

			Arbre arbre = new Arbre(individu.getMonde(), profondeur);
			
			int nombreNoeud = individu.getArbre().nbNoeud(profondeurAleatoire);
			int noeudAleatoire = (int) (Math.random() * nombreNoeud);
			
			Noeud noeud = individu.getArbre().getNoeud(profondeurAleatoire, noeudAleatoire);
			Noeud pere = noeud.getPere();
			// Changement de pere
			noeud.addPere(pere);
			// Changement de fils
			pere.changeFils(noeud, arbre.getNoeud());
			
			population.add(individu);
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
		this.tournoi();

		System.out.println("Tournoi terminé: NB="+this.getNombreIndividu());

		this.croisement();

		System.out.println("Croisement terminé: NB="+this.getNombreIndividu());
		
		this.mutation();
		
		System.out.println("Mutation terminé: NB="+this.getNombreIndividu());
		
		this.ajoutVainqueur();
		
		System.out.println("Mutation terminé: NB="+this.getNombreIndividu());
				
		enSimulation = false;
		nbGeneration++;
	}

	private void ajoutVainqueur() {
		population.addAll(vainqueur);
	}

	public void reinitialisation(Map map, Graphe graphe) {
		List<Individu> tmp = new ArrayList<Individu>();

		for(Individu i: population) {
			tmp.add(new Individu(map, graphe, i.getArbre(), 0));
		}
		
		population = tmp;
	}
}
