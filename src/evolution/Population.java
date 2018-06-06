package evolution;

import java.util.ArrayList;
import java.util.List;

import arbre.Arbre;
import arbre.Feuille;
import arbre.Noeud;
import graphe.Graphe;
import model.Map;
import model.Personnage;

public class Population {
	private int PROFONDEUR;
	private int NOMBREPOPULATION;
	private int NBSELECTION;
	private int NBVAINQUEUR;
	private double POURCENTAGEMUTATION;

	private int nbGeneration;
	private List<Individu> population, vainqueur;
	private List<Thread> liste;
	
	private Information[][] informations;

	private boolean aJoue;

	// Population initiale
	public Population(Map map, Graphe graphe, int PROFONDEUR, int NOMBREPOPULATION, int NBSELECTION, int NBVAINQUEUR, double POURCENTAGEMUTATION) {
		this.PROFONDEUR = PROFONDEUR;
		this.NOMBREPOPULATION = NOMBREPOPULATION;
		this.NBSELECTION = NBSELECTION;
		this.NBVAINQUEUR = NBVAINQUEUR;
		this.POURCENTAGEMUTATION = POURCENTAGEMUTATION;
		
		this.informations = new Information[100][NOMBREPOPULATION];

		this.nbGeneration = 0;
		aJoue = false;

		// On crée les individus
		this.population = new ArrayList<Individu>();
		for(int nb=0 ; nb<NOMBREPOPULATION ; nb++) {
			population.add(new Individu(map, graphe, this.PROFONDEUR));
		}
	}

	public int getNombrePopulation() { return NOMBREPOPULATION; }
	public Individu getIndividu(int index) { return population.get(index); }
	public int getNombreIndividu() { return population.size(); }
	public int getNombreGeneration() { return nbGeneration; }
	public Information getInformation(int generation, int individu) { return this.informations[generation][individu]; }


	// Cette fct a été checké
	private void tournoi() {
		int nbSupprimer;
		this.vainqueur = new ArrayList<Individu>();

		// On "tue" le surplus de population aléatoirement
		nbSupprimer = NOMBREPOPULATION-NBSELECTION;

		for (int i=0 ; i<nbSupprimer ; i++) {
			int random = (int) (Math.random() * this.getNombreIndividu());

			this.informations[this.nbGeneration][random].setPasTournoi();
			
			population.remove(random);
		}

		// On cherche les meilleurs
		
		for (int i=0 ; i<NBVAINQUEUR ; i++) {
			int max = -1;
			int index = 0;
			int indexMeilleur = -1;
			Individu leMeilleur = null;
			for(Individu m: population) {
				if (m.getScore()>max) {
					max = m.getScore();
					leMeilleur = m;
					indexMeilleur = index;
				}
				
				index++;
			}

			// et ont les extraits
			if (leMeilleur!=null) {
				this.informations[this.nbGeneration][indexMeilleur].setEstVainqueur();
				
				vainqueur.add(leMeilleur);
				population.remove(leMeilleur);
			}
		}

		// Puis on "tue" le reste
		population.clear();
	}

	/**
	 * Croisement equilibré
	 * L'index 0 n'est pas prit en compte
	 * Cette fct a été checké
	 * @param individu_1 Un individu non cloné
	 * @param individu_2 Un individu non cloné
	 * @return ajoute 2 enfants à la population
	 */
	private void croisementEquilibre(Individu individu_1, Individu individu_2) {
		int profondeurMin = 1;
		int profondeurMax = PROFONDEUR;
		// Pour profondeur 10: aleatoire de 1 à 9
		int profondeurAleatoire = (int) (profondeurMin + (Math.random() * (profondeurMax-profondeurMin))); 

		// INDIVIDU 1
		Individu individuP1 = individu_1.clone();
		// int nombreNoeud_P1 = individuP1.getArbre().nbNoeud(profondeurAleatoire);
		int nombreNoeud_P1 = (int) Math.pow(2, profondeurAleatoire); // on ne le calcul pas car equilibre
		int noeudAleatoire_P1 = (int) (Math.random() * nombreNoeud_P1);
		Noeud noeudP1 = individuP1.getArbre().getNoeud(profondeurAleatoire, noeudAleatoire_P1);

		// INDIVIDU 2
		Individu individuP2 = individu_2.clone();
		// int nombreNoeud_P2 = individuP2.getArbre().nbNoeud(profondeurAleatoire);
		int nombreNoeud_P2 = (int) Math.pow(2, profondeurAleatoire); // on ne le calcul pas car equilibre
		int noeudAleatoire_P2 = (int) (Math.random() * nombreNoeud_P2);
		Noeud noeudP2 = individuP2.getArbre().getNoeud(profondeurAleatoire, noeudAleatoire_P2);

		// INVERSION
		Noeud pere1 = noeudP1.getPere();
		Noeud pere2 = noeudP2.getPere();
		// Changement de pere
		noeudP1.addPere(pere2);
		noeudP2.addPere(pere1);
		// Changement de fils
		if (pere1!=null) {
			pere1.changeFils(noeudP1, noeudP2);
		} else {
			System.err.println("ERREUR = CroisementEquilibre: pere1 n'existe pas.");
			System.exit(0);
		}

		if (pere2!=null) {
			pere2.changeFils(noeudP2, noeudP1);
		} else {
			System.err.println("ERREUR = CroisementEquilibre: pere2 n'existe pas.");
			System.exit(0);
		}

		// On ajoute à la nouvelle génération
		population.add(individuP1);
		population.add(individuP2);
	}

	/**
	 * Fonction de croisement sur arbre equilibre
	 * Cette fct a été checké
	 * @param nbACreer le nombre d'individu à mettre dans la population
	 * @param liste vainqueur
	 * @return la population est recrée suite à ça 
	 */
	private void croisement(int nbACreer) {
		int nbParGeneration = (NBVAINQUEUR*NBVAINQUEUR)-NBVAINQUEUR;
		int nbGeneration = nbACreer/nbParGeneration;
		int nbIndividuRestant = (int) (nbACreer%nbParGeneration);

		/* **************************** */
		for(int i=0 ; i<nbGeneration ; i++) {
			// On croise tout le monde
			for(int p1=0 ; p1<NBVAINQUEUR ; p1++) {
				for(int p2=p1+1 ; p2<NBVAINQUEUR ; p2++) {
					croisementEquilibre(vainqueur.get(p1),vainqueur.get(p2));
				}
			}
		}

		// On comble avec les deux meilleurs
		for (int i=0 ; i<nbIndividuRestant/2 ; i++) {
			croisementEquilibre(vainqueur.get(0),vainqueur.get(1));
		}

		if(population.size()!=NOMBREPOPULATION) {
			System.err.println("ERREUR = Croisement: la population est différente: "+population.size()+" individus.");
			System.exit(0);
		}
	}

	// Fonction pas checké: à l'abandon
	private void croisementNonEquilibre() {
		int nbACreer = NOMBREPOPULATION;

		int nbParGeneration = (int) (Math.pow(NBVAINQUEUR,2)-NBVAINQUEUR);
		int nbGeneration = nbACreer/nbParGeneration;
		int reste = (int) (nbACreer%nbParGeneration);
		//System.out.println("nbParGeneration: "+nbParGeneration+" nbGeneration: "+nbGeneration+" reste: "+reste);

		/* **************************** */
		for(int i=0 ; i<nbGeneration ; i++) {
			// On croise tout le monde
			for(int p1=0 ; p1<NBVAINQUEUR ; p1++) {

				for(int p2=p1+1 ; p2<NBVAINQUEUR ; p2++) {
					int profondeurP1 = vainqueur.get(p1).getArbre().hauteur();
					int profondeurP2 = vainqueur.get(p2).getArbre().hauteur();

					Individu individuP1 = null;
					Individu individuP2 = null;

					if(profondeurP1<=profondeurP2) {
						// si P1 est plus petit alors on commence par lui 
						individuP1 = vainqueur.get(p1).clone();
						individuP2 = vainqueur.get(p2).clone();
					} else {
						// sinon on commence par P2
						individuP1 = vainqueur.get(p2).clone();
						individuP2 = vainqueur.get(p1).clone();
					}


					// INDIVIDU 1
					int profondeurMinP1 = 0;
					int profondeurMaxP1 = individuP1.getArbre().hauteur();
					int profondeurAleatoireP1 = (int) (profondeurMinP1 + (Math.random() * (profondeurMaxP1-profondeurMinP1))); // en 1 et la profondeur

					int nombreNoeudP1 = individuP1.getArbre().nbNoeud(profondeurAleatoireP1);
					int noeudAleatoireP1 = (int) (Math.random() * nombreNoeudP1);

					Noeud noeudP1 = individuP1.getArbre().getNoeud(profondeurAleatoireP1, noeudAleatoireP1);

					// INDIVIDU 2
					int profondeurMinP2 = 0;
					int profondeurMaxP2 = profondeurAleatoireP1;

					int profondeurAleatoireP2 = (int) (profondeurMinP2 + (Math.random() * (profondeurMaxP2-profondeurMinP2))); // en 1 et la profondeur

					int nombreNoeudP2 = individuP2.getArbre().nbNoeud(profondeurAleatoireP1);
					int noeudAleatoireP2 = (int) (Math.random() * nombreNoeudP2);
					Noeud noeudP2 = individuP2.getArbre().getNoeud(profondeurAleatoireP1, noeudAleatoireP2);

					// INVERSION
					Noeud pere1 = noeudP1.getPere();
					Noeud pere2 = noeudP2.getPere();

					// Changement de pere
					noeudP1.addPere(pere2);
					noeudP2.addPere(pere1);
					// Changement de fils
					if(pere1!=null) {
						pere1.changeFils(noeudP1, noeudP2);
					}

					if(pere2!=null) {
						pere2.changeFils(noeudP2, noeudP1);
					}

					// On ajoute à la nouvelle génération
					population.add(individuP1);
					population.add(individuP2);

					int nouvelleprofondeurMaxP1 =  individuP1.getArbre().hauteur();
					int nouvelleprofondeurMaxP2 =  individuP2.getArbre().hauteur();

					if(nouvelleprofondeurMaxP1>PROFONDEUR) {
						System.out.println("nouvelleprofondeurMaxP1:"+nouvelleprofondeurMaxP1);
						System.out.println("profondeurMinP1:"+profondeurMinP1);
						System.out.println("profondeurMaxP1:"+profondeurMaxP1);
						System.out.println("profondeurAleatoireP1:"+profondeurAleatoireP1);
						System.out.println("nombreNoeudP1:"+nombreNoeudP1);
						System.out.println("noeudAleatoireP1:"+noeudAleatoireP1);
						System.out.println("");
						System.out.println("nouvelleprofondeurMaxP2:"+nouvelleprofondeurMaxP2);
						System.out.println("profondeurMinP2:"+profondeurMinP2);
						System.out.println("profondeurMaxP2:"+profondeurMaxP2);
						System.out.println("profondeurAleatoireP2:"+profondeurAleatoireP2);
						System.out.println("nombreNoeudP2:"+nombreNoeudP2);
						System.out.println("noeudAleatoireP2:"+noeudAleatoireP2);

						System.exit(0);
					}

					if(nouvelleprofondeurMaxP2>PROFONDEUR) {
						System.out.println("nouvelleprofondeurMaxP2:"+nouvelleprofondeurMaxP2);
						System.out.println("profondeurMinP2:"+profondeurMinP2);
						System.out.println("profondeurMaxP2:"+profondeurMaxP2);
						System.out.println("profondeurAleatoireP2:"+profondeurAleatoireP2);
						System.out.println("nombreNoeudP2:"+nombreNoeudP2);
						System.out.println("noeudAleatoireP2:"+noeudAleatoireP2);
						noeudP2.affiche();
						System.out.println("");
						System.out.println("nouvelleprofondeurMaxP1:"+nouvelleprofondeurMaxP1);
						System.out.println("profondeurMinP1:"+profondeurMinP1);
						System.out.println("profondeurMaxP1:"+profondeurMaxP1);
						System.out.println("profondeurAleatoireP1:"+profondeurAleatoireP1);
						System.out.println("nombreNoeudP1:"+nombreNoeudP1);
						System.out.println("noeudAleatoireP1:"+noeudAleatoireP1);
						noeudP1.affiche();

						System.exit(0);
					}
				}
			}
		}

		// On comble avec les deux meilleurs
		for (int i=0 ; i<reste/2 ; i++) {

		}
	}

	/**
	 * Croisement non equilibré
	 * L'index 0 est prit en compte
	 * Cette fct a été checké
	 * @param individu_1 Un individu non cloné, il sera la base de l'enfant
	 * @param individu_2 Un individu non cloné, l'individu dont on pique un gène
	 * @return ajoute 1 enfants à la population
	 */
	private void croisementNonEquilibreNonEquivalent(Individu individu_1, Individu individu_2) {
		Individu individuP1 = individu_1.clone();
		Individu individuP2 = individu_2.clone();

		// INDIVIDU 1
		int hauteurP1 = individuP1.getArbre().hauteur();
		int profondeurMinP1 = 0;
		int profondeurMaxP1 = hauteurP1;
		int profondeurAleatoireP1 = (int) (profondeurMinP1 + (Math.random() * (profondeurMaxP1-profondeurMinP1)));

		int nombreNoeudP1 = individuP1.getArbre().nbNoeud(profondeurAleatoireP1);
		int noeudAleatoireP1 = (int) (Math.random() * nombreNoeudP1);

		Noeud noeudP1 = individuP1.getArbre().getNoeud(profondeurAleatoireP1, noeudAleatoireP1);
		int placeDisponibleP1 = hauteurP1-profondeurAleatoireP1;

		// INDIVIDU 2
		int hauteurP2 = individuP2.getArbre().hauteur();
		int profondeurMinP2 = hauteurP2-placeDisponibleP1;
		if(profondeurMinP2<0) {
			profondeurMinP2 = 0;
		}
		int profondeurMaxP2 = hauteurP2;
		int profondeurAleatoireP2 = (int) (profondeurMinP2 + (Math.random() * (profondeurMaxP2-profondeurMinP2)));

		int nombreNoeudP2 = individuP2.getArbre().nbNoeud(profondeurAleatoireP2);
		int noeudAleatoireP2 = (int) (Math.random() * nombreNoeudP2);

		Noeud noeudP2 = individuP2.getArbre().getNoeud(profondeurAleatoireP2, noeudAleatoireP2);

		// INVERSION
		Noeud pere1 = noeudP1.getPere();

		// Changement de pere
		noeudP2.addPere(pere1);

		// Changement de fils
		if(pere1!=null) {
			pere1.changeFils(noeudP1, noeudP2);
		}

		// On ajoute à la nouvelle génération
		population.add(individuP1);

		int nouvelleprofondeurMaxP1 =  individuP1.getArbre().hauteur();

		if (nouvelleprofondeurMaxP1>PROFONDEUR) {
			System.err.println("ERREUR = croisementNonEquilibreNonEquivalent: profondeur finale trop haute:"+nouvelleprofondeurMaxP1);
			System.exit(0);
		}
	}


	/**
	 * Fonction de croisement sur arbre non equilibre
	 * Cette fct a été checké
	 * @param nbACreer le nombre d'individu à mettre dans la population
	 * @param liste vainqueur
	 * @return la population est recrée suite à ça 
	 */
	private void croisementNonEquilibreNonEquivalent(int nbACreer) {
		int nbParGeneration = (NBVAINQUEUR*NBVAINQUEUR)-NBVAINQUEUR;
		int nbGeneration = nbACreer/nbParGeneration;
		int nbIndividuRestant = (int) (nbACreer%nbParGeneration);

		/* **************************** */
		for(int i=0 ; i<nbGeneration ; i++) {
			// On croise tout le monde
			for(int p1=0 ; p1<NBVAINQUEUR ; p1++) {
				for(int p2=p1+1 ; p2<NBVAINQUEUR ; p2++) {
					croisementNonEquilibreNonEquivalent(vainqueur.get(p1),vainqueur.get(p2));
					croisementNonEquilibreNonEquivalent(vainqueur.get(p2),vainqueur.get(p1));
				}
			}
		}

		// On comble avec les deux meilleurs
		for (int i=0 ; i<nbIndividuRestant/2 ; i++) {
			croisementNonEquilibreNonEquivalent(vainqueur.get(0),vainqueur.get(1));
			croisementNonEquilibreNonEquivalent(vainqueur.get(1),vainqueur.get(0));
		}
		
		for (int i=0 ; i<nbIndividuRestant%2 ; i++) {
			croisementNonEquilibreNonEquivalent(vainqueur.get(0),vainqueur.get(1));
		}

		/*if(population.size()!=NOMBREPOPULATION) {
			System.err.println("ERREUR = Croisement: la population est différente: "+population.size()+" individus.");
			System.exit(0);
		}*/
	}

	private void mutation() {
		if (POURCENTAGEMUTATION!=0) {
			for(int i=0 ; i<this.getNombreIndividu() ; i++) {
				int aleatoire = (int) (Math.random() * (NOMBREPOPULATION/(POURCENTAGEMUTATION*NOMBREPOPULATION)));

				if(aleatoire==0) {
					Individu individu = this.getIndividu(i);
					int profondeur = PROFONDEUR;
					int profondeurMin = 0;
					int profondeurMax = profondeur;

					int profondeurAleatoire = (int) (profondeurMin + (Math.random() * (profondeurMax-profondeurMin))); 
					Noeud nouveauNoeud = null;

					int placeLibre = profondeur-profondeurAleatoire;
					if (placeLibre == 1) {
						nouveauNoeud = new Feuille(null, Personnage.directionAleatoire());
					} else {
						Arbre arbre = new Arbre(individu.getMonde(), placeLibre);
						nouveauNoeud = arbre.getNoeud();
					}

					int nombreNoeud = (int) Math.pow(2, profondeurAleatoire); // on ne le calcul pas car equilibre
					int noeudAleatoire = (int) (Math.random() * nombreNoeud);

					Noeud noeud = individu.getArbre().getNoeud(profondeurAleatoire, noeudAleatoire);
					Noeud pere = noeud.getPere();
					// Changement de pere
					nouveauNoeud.addPere(pere);
					// Changement de fils
					pere.changeFils(noeud, nouveauNoeud);

					int hauteur = individu.getArbre().hauteur();
					if (hauteur!=PROFONDEUR) { 
						System.err.println("ERREUR =  mutation: différence de profondeur "+PROFONDEUR+": "+hauteur);
						System.exit(0);
					}
				}
			}
		}
	}

	private void mutationNonEquilibre() {
		if (POURCENTAGEMUTATION!=0) {
			for(int i=0 ; i<this.getNombreIndividu() ; i++) {
				int aleatoire = (int) (Math.random() * (NOMBREPOPULATION/(POURCENTAGEMUTATION*NOMBREPOPULATION)));

				if(aleatoire==0) {
					Individu individu = this.getIndividu(i);

					int profondeur = individu.getArbre().hauteur();
					int profondeurMin = 0;
					int profondeurMax = profondeur;

					int profondeurAleatoire = (int) (profondeurMin + (Math.random() * (profondeurMax-profondeurMin)));
					Noeud nouveauNoeud = null;

					int tailleMin = 1; // soit une feuille
					int tailleMax = PROFONDEUR-profondeurAleatoire;

					int taillealeatoire = (int) (tailleMin + (Math.random() * (tailleMax-tailleMin)));

					if (taillealeatoire == 1) {
						nouveauNoeud = new Feuille(null, Personnage.directionAleatoire());
					} else {
						Arbre arbre = new Arbre(individu.getMonde(), taillealeatoire);
						nouveauNoeud = arbre.getNoeud();
					}

					int nombreNoeud = individu.getArbre().nbNoeud(profondeurAleatoire);
					int noeudAleatoire = (int) (Math.random() * nombreNoeud);

					Noeud noeud = individu.getArbre().getNoeud(profondeurAleatoire, noeudAleatoire);
					Noeud pere = noeud.getPere();
					// Changement de pere
					nouveauNoeud.addPere(pere);
					// Changement de fils
					if(pere!=null) {
						pere.changeFils(noeud, nouveauNoeud);
					}	

					int nouvelleprofondeurMax =  individu.getArbre().hauteur();
					if(nouvelleprofondeurMax>PROFONDEUR) {
						System.out.println("profondeurMin:"+profondeurMin);
						System.out.println("profondeurMax:"+profondeurMax);
						System.out.println("profondeurAleatoire:"+profondeurAleatoire);
						System.out.println("taillealeatoire:"+taillealeatoire);
						System.out.println("nombreNoeud:"+nombreNoeud);
						System.out.println("noeudAleatoire:"+noeudAleatoire);
						System.err.println("ERREUR = mutationNonEquilibre: profondeur "+PROFONDEUR+": "+nouvelleprofondeurMax);
						System.exit(0);
					}
				}
			}
		}
	}

	public Individu meilleurIndividu() {
		// Tout est fini
		int max = -1;
		Individu individu = null;
		for(Individu m:population) {
			if (m.getScore()>max) {
				max = m.getScore();
				individu = m;
			}	
		}

		if(individu!=null) {
			System.out.println("!!!!!!!! Le meilleur: score="+individu.getScore());
			return individu;
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
		if (!aJoue) {
			// On crée les threads
			this.liste = new ArrayList<Thread>();
			for (int i=0 ; i<NOMBREPOPULATION ; i++) {
				liste.add(new Thread(this.getIndividu(i)));
			}

			// On lance l'execution
			for (int i=0 ; i<this.getNombreIndividu() ; i++) {
				liste.get(i).start();
			}

			aJoue = true;
		}
	}

	public void lancerSelection() {
		if (aJoue) {
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

			// Tout le monde à arrêté de jouer
			for(int i=0; i<this.NOMBREPOPULATION ; i++) {
				this.informations[this.nbGeneration][i] = new Information();
				this.informations[this.nbGeneration][i].setArbre(population.get(i).getArbre());
				this.informations[this.nbGeneration][i].setScore(population.get(i).getScore());
			}
			
			this.tournoi();

			//this.croisement(NOMBREPOPULATION);
			//this.croisementNonEquilibre();
			this.croisementNonEquilibreNonEquivalent(NOMBREPOPULATION);

			//this.mutation();
			this.mutationNonEquilibre();

			//this.ajoutVainqueur();

			nbGeneration++;
			aJoue = false;

		} else {
			System.out.println("La population n'a pas joué");
		}
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
