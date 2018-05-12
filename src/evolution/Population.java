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

	private boolean aJoue;

	// Population initiale
	public Population(Map map, Graphe graphe, int PROFONDEUR, int NOMBREPOPULATION, int NBSELECTION, int NBVAINQUEUR, double POURCENTAGEMUTATION) {
		this.PROFONDEUR = PROFONDEUR;
		this.NOMBREPOPULATION = NOMBREPOPULATION;
		this.NBSELECTION = NBSELECTION;
		this.NBVAINQUEUR = NBVAINQUEUR;
		this.POURCENTAGEMUTATION = POURCENTAGEMUTATION;

		this.nbGeneration = 1;
		aJoue = false;

		// On crée les individus
		this.population = new ArrayList<Individu>();
		for(int nb=0 ; nb<NOMBREPOPULATION ; nb++) {
			population.add(new Individu(map, graphe,this.PROFONDEUR));
		}
	}

	public int getNombrePopulation() { return NOMBREPOPULATION; }
	public Individu getIndividu(int index) { return population.get(index); }
	public int getNombreIndividu() { return population.size(); }
	public int getNombreGeneration() { return nbGeneration; }

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
		int nbACreer = NOMBREPOPULATION/*-NBVAINQUEUR*/;

		int nbParGeneration = (int) (Math.pow(NBVAINQUEUR,2)-NBVAINQUEUR);
		int nbGeneration = nbACreer/nbParGeneration;
		int reste = (int) (nbACreer%nbParGeneration);
		//System.out.println("nbParGeneration: "+nbParGeneration+" nbGeneration: "+nbGeneration+" reste: "+reste);

		/* **************************** */
		for(int i=0 ; i<nbGeneration ; i++) {
			// On croise tout le monde
			for(int p1=0 ; p1<NBVAINQUEUR ; p1++) {
				for(int p2=p1+1 ; p2<NBVAINQUEUR ; p2++) {					
					// INDIVIDU 1
					Individu individuP1 = vainqueur.get(p1).clone();

					int profondeur = individuP1.getArbre().getProfondeur();
					int profondeurAleatoire = (int) (1 + (Math.random() * (profondeur-1))); // en 1 et la profondeur

					int nombreNoeud = individuP1.getArbre().nbNoeud(profondeurAleatoire);
					int noeudAleatoire = (int) (Math.random() * nombreNoeud);

					Noeud noeudP1 = individuP1.getArbre().getNoeud(profondeurAleatoire, noeudAleatoire);

					// INDIVIDU 2
					Individu individuP2 = vainqueur.get(p2).clone();

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
		}

		// On comble avec les deux meilleurs
		for (int i=0 ; i<reste/2 ; i++) {
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

	public void croisementNonEquilibreNonEquivalent() {
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

					if(profondeurP1>=profondeurP2) {
						// si P1 est plus grand alors on commence par lui 
						individuP1 = vainqueur.get(p1).clone();
						individuP2 = vainqueur.get(p2).clone();
					} else {
						// sinon on commence par P2
						individuP1 = vainqueur.get(p2).clone();
						individuP2 = vainqueur.get(p1).clone();
					}

					// INDIVIDU 1

					// P2 est forcement le plus petit

					int profondeurMinP2_1 = 0;
					int profondeurMaxP2_1 = individuP2.getArbre().hauteur();
					int profondeurAleatoireP2_1 = (int) (profondeurMinP2_1 + (Math.random() * (profondeurMaxP2_1-profondeurMinP2_1)));

					int nombreNoeudP2_1 = individuP2.getArbre().nbNoeud(profondeurAleatoireP2_1);
					int noeudAleatoireP2_1 = (int) (Math.random() * nombreNoeudP2_1);

					Noeud noeudP2_1 = individuP2.getArbre().getNoeud(profondeurAleatoireP2_1, noeudAleatoireP2_1);

					int profondeurMinP1_1 = 0;
					int profondeurMaxP1_1 = individuP1.getArbre().hauteur()-(profondeurMaxP2_1-profondeurAleatoireP2_1);
					int profondeurAleatoireP1_1 = (int) (profondeurMinP1_1 + (Math.random() * (profondeurMaxP1_1-profondeurMinP1_1)));

					int nombreNoeudP1_1 = individuP1.getArbre().nbNoeud(profondeurAleatoireP1_1);
					int noeudAleatoireP1_1 = (int) (Math.random() * nombreNoeudP1_1);

					/*System.out.println("");
					System.out.println("profondeurMinP1_1:"+profondeurMinP1_1);
					System.out.println("profondeurMaxP1_1:"+profondeurMaxP1_1);
					System.out.println(" - profondeurAleatoireP1_1:"+profondeurAleatoireP1_1);
					System.out.println("nombreNoeudP1_1:"+nombreNoeudP1_1);
					System.out.println(" - noeudAleatoireP1_1:"+noeudAleatoireP1_1);
					noeudP2_1.affiche();*/
					Noeud noeudP1_1 = individuP1.getArbre().getNoeud(profondeurAleatoireP1_1, noeudAleatoireP1_1);


					// INDIVIDU 2

					// P1 est forcement plus grand
					int profondeurMinP1_2 = 0;
					int profondeurMaxP1_2 = individuP1.getArbre().hauteur();
					int profondeurAleatoireP1_2 = (int) (profondeurMinP1_2 + (Math.random() * (profondeurMaxP1_2-profondeurMinP1_2)));

					int nombreNoeudP1_2 = individuP1.getArbre().nbNoeud(profondeurAleatoireP1_2);
					int noeudAleatoireP1_2 = (int) (Math.random() * nombreNoeudP1_2);

					Noeud noeudP1_2 = individuP1.getArbre().getNoeud(profondeurAleatoireP1_2, noeudAleatoireP1_2);

					int profondeurP2_2 = individuP2.getArbre().hauteur();
					int profondeurMinP2_2 = 0;
					int profondeurMaxP2_2 = (PROFONDEUR-1)-(profondeurMaxP1_2-profondeurAleatoireP1_2);
					
					if (profondeurMaxP2_2>profondeurP2_2) {
						profondeurMaxP2_2 = profondeurP2_2;
					}
					
					int profondeurAleatoireP2_2 = (int) (profondeurMinP2_2 + (Math.random() * (profondeurMaxP2_2-profondeurMinP2_2)));

					int nombreNoeudP2_2 = individuP2.getArbre().nbNoeud(profondeurAleatoireP2_2);
					int noeudAleatoireP2_2 = (int) (Math.random() * nombreNoeudP2_2);

					/*System.out.println("profondeurMinP1_2:"+profondeurMinP1_2);
					System.out.println("profondeurMaxP1_2:"+profondeurMaxP1_2);
					System.out.println("profondeurAleatoireP1_2:"+profondeurAleatoireP1_2);
					System.out.println("nombreNoeudP1_2:"+nombreNoeudP1_2);
					System.out.println("noeudAleatoireP1_2:"+noeudAleatoireP1_2);
					System.out.println(" profondeurMinP2_2:"+profondeurMinP2_2);
					System.out.println(" profondeurMaxP2_2:"+profondeurMaxP2_2);
					System.out.println(" profondeurAleatoireP2_2:"+profondeurAleatoireP2_2);
					System.out.println(" nombreNoeudP2_2:"+nombreNoeudP2_2);
					System.out.println(" noeudAleatoireP2_2:"+noeudAleatoireP2_2);
					System.out.println("");*/
					Noeud noeudP2_2 = individuP2.getArbre().getNoeud(profondeurAleatoireP2_2, noeudAleatoireP2_2);

					// INVERSION
					Noeud pere1_1 = noeudP1_1.getPere();
					Noeud pere2_2 = noeudP2_2.getPere();

					// Changement de pere
					noeudP2_1.addPere(pere1_1);
					noeudP1_2.addPere(pere2_2);

					// Changement de fils
					if(pere1_1!=null) {
						pere1_1.changeFils(noeudP1_1, noeudP2_1);
					}
					
					if(pere2_2!=null) {
						pere2_2.changeFils(noeudP2_2, noeudP1_2);
					}

					// On ajoute à la nouvelle génération
					population.add(individuP1);
					population.add(individuP2);

					int nouvelleprofondeurMaxP1 =  individuP1.getArbre().hauteur();
					int nouvelleprofondeurMaxP2 =  individuP2.getArbre().hauteur();

					if(nouvelleprofondeurMaxP1>PROFONDEUR) {
						System.out.println("nouvelleprofondeurMaxP1_1:"+nouvelleprofondeurMaxP1);
						System.out.println("profondeurMinP1_1:"+profondeurMinP1_1);
						System.out.println("profondeurMaxP1_1:"+profondeurMaxP1_1);
						System.out.println("profondeurAleatoireP1_1:"+profondeurAleatoireP1_1);
						System.out.println("nombreNoeudP1_1:"+nombreNoeudP1_1);
						System.out.println("noeudAleatoireP1_1:"+noeudAleatoireP1_1);
						System.out.println("");
						noeudP1_1.affiche();
						System.out.println("");
						System.out.println("nouvelleprofondeurMaxP2_1:"+nouvelleprofondeurMaxP2);
						System.out.println("profondeurMinP2_1:"+profondeurMinP2_1);
						System.out.println("profondeurMaxP2_1:"+profondeurMaxP2_1);
						System.out.println("profondeurAleatoireP2_1:"+profondeurAleatoireP2_1);
						System.out.println("nombreNoeudP2_1:"+nombreNoeudP2_1);
						System.out.println("noeudAleatoireP2_1:"+noeudAleatoireP2_1);
						System.out.println("");
						noeudP2_1.affiche();
						System.exit(0);
					}

					if(nouvelleprofondeurMaxP2>PROFONDEUR) {
						System.out.println("nouvelleprofondeurMaxP2_2:"+nouvelleprofondeurMaxP2);
						System.out.println("profondeurMinP2_2:"+profondeurMinP2_2);
						System.out.println("profondeurMaxP2_2:"+profondeurMaxP2_2);
						System.out.println("profondeurAleatoireP2_2:"+profondeurAleatoireP2_2);
						System.out.println("nombreNoeudP2_2:"+nombreNoeudP2_2);
						System.out.println("noeudAleatoireP2_2:"+noeudAleatoireP2_2);
						noeudP2_2.affiche();
						System.out.println("");
						System.out.println("nouvelleprofondeurMaxP1_2:"+nouvelleprofondeurMaxP1);
						System.out.println("profondeurMinP1_2:"+profondeurMinP1_2);
						System.out.println("profondeurMaxP1_2:"+profondeurMaxP1_2);
						System.out.println("profondeurAleatoireP1_2:"+profondeurAleatoireP1_2);
						System.out.println("nombreNoeudP1_2:"+nombreNoeudP1_2);
						System.out.println("noeudAleatoireP1_2:"+noeudAleatoireP1_2);
						noeudP1_2.affiche();

						System.exit(0);
					}
				}
			}
		}

		// On comble avec les deux meilleurs
		for (int i=0 ; i<reste/2 ; i++) {
			int profondeurMaxP1 = vainqueur.get(0).getArbre().hauteur();
			int profondeurMaxP2 = vainqueur.get(1).getArbre().hauteur();

			Individu individuP1 = null;
			Individu individuP2 = null;

			if(profondeurMaxP1>=profondeurMaxP2) {
				// si P1 est plus petit alors on commence par lui 
				individuP1 = vainqueur.get(0).clone();
				individuP2 = vainqueur.get(1).clone();
			} else {
				// sinon on commence par P2
				individuP1 = vainqueur.get(1).clone();
				individuP2 = vainqueur.get(0).clone();
			}

			// INDIVIDU 1

			// P2 est forcement le plus petit

			int profondeurMinP2_1 = 0;

			int profondeurMaxP2_1 = individuP2.getArbre().hauteur();
			int profondeurAleatoireP2_1 = (int) (profondeurMinP2_1 + (Math.random() * (profondeurMaxP2_1-profondeurMinP2_1)));

			int nombreNoeudP2_1 = individuP2.getArbre().nbNoeud(profondeurAleatoireP2_1);
			int noeudAleatoireP2_1 = (int) (Math.random() * nombreNoeudP2_1);

			Noeud noeudP2_1 = individuP2.getArbre().getNoeud(profondeurAleatoireP2_1, noeudAleatoireP2_1);

			int profondeurMinP1_1 = 0;
			int profondeurMaxP1_1 = individuP1.getArbre().hauteur()-(profondeurMaxP2_1-profondeurAleatoireP2_1);
			int profondeurAleatoireP1_1 = (int) (profondeurMinP1_1 + (Math.random() * (profondeurMaxP1_1-profondeurMinP1_1)));

			int nombreNoeudP1_1 = individuP1.getArbre().nbNoeud(profondeurAleatoireP1_1);
			int noeudAleatoireP1_1 = (int) (Math.random() * nombreNoeudP1_1);

			Noeud noeudP1_1 = individuP1.getArbre().getNoeud(profondeurAleatoireP1_1, noeudAleatoireP1_1);


			// INDIVIDU 2

			// P1 est forcement plus grand
			int profondeurMinP1_2 = 0;
			int profondeurMaxP1_2 = individuP1.getArbre().hauteur();
			int profondeurAleatoireP1_2 = (int) (profondeurMinP1_2 + (Math.random() * (profondeurMaxP1_2-profondeurMinP1_2)));

			int nombreNoeudP1_2 = individuP1.getArbre().nbNoeud(profondeurAleatoireP1_2);
			int noeudAleatoireP1_2 = (int) (Math.random() * nombreNoeudP1_2);

			Noeud noeudP1_2 = individuP1.getArbre().getNoeud(profondeurAleatoireP1_2, noeudAleatoireP1_2);

			int profondeurMinP2_2 = 0;
			int profondeurMaxP2_2 = individuP2.getArbre().hauteur()-(profondeurMaxP1_2-profondeurAleatoireP1_2);
			int profondeurAleatoireP2_2 = (int) (profondeurMinP2_2 + (Math.random() * (profondeurMaxP2_2-profondeurMinP2_2)));

			int nombreNoeudP2_2 = individuP2.getArbre().nbNoeud(profondeurAleatoireP2_2);
			int noeudAleatoireP2_2 = (int) (Math.random() * nombreNoeudP2_2);

			Noeud noeudP2_2 = individuP2.getArbre().getNoeud(profondeurAleatoireP2_2, noeudAleatoireP2_2);

			// INVERSION
			Noeud pere1_1 = noeudP1_1.getPere();
			Noeud pere2_2 = noeudP2_2.getPere();

			// Changement de pere
			noeudP2_1.addPere(pere1_1);
			noeudP1_2.addPere(pere2_2);

			// Changement de fils
			if(pere1_1!=null) {
				pere1_1.changeFils(noeudP1_1, noeudP2_1);
			}
			
			if(pere2_2!=null) {
				pere2_2.changeFils(noeudP2_2, noeudP1_2);
			}
	

			// On ajoute à la nouvelle génération
			population.add(individuP1);
			population.add(individuP2);

			int nouvelleprofondeurMaxP1 =  individuP1.getArbre().hauteur();
			int nouvelleprofondeurMaxP2 =  individuP2.getArbre().hauteur();

			if(nouvelleprofondeurMaxP1>PROFONDEUR) {
				System.out.println("nouvelleprofondeurMaxP1_1:"+nouvelleprofondeurMaxP1);
				System.out.println("profondeurMinP1_1:"+profondeurMinP1_1);
				System.out.println("profondeurMaxP1_1:"+profondeurMaxP1_1);
				System.out.println("profondeurAleatoireP1_1:"+profondeurAleatoireP1_1);
				System.out.println("nombreNoeudP1_1:"+nombreNoeudP1_1);
				System.out.println("noeudAleatoireP1_1:"+noeudAleatoireP1_1);
				System.out.println("");
				noeudP1_1.affiche();
				System.out.println("");
				System.out.println("nouvelleprofondeurMaxP2_1:"+nouvelleprofondeurMaxP2);
				System.out.println("profondeurMinP2_1:"+profondeurMinP2_1);
				System.out.println("profondeurMaxP2_1:"+profondeurMaxP2_1);
				System.out.println("profondeurAleatoireP2_1:"+profondeurAleatoireP2_1);
				System.out.println("nombreNoeudP2_1:"+nombreNoeudP2_1);
				System.out.println("noeudAleatoireP2_1:"+noeudAleatoireP2_1);
				System.out.println("");
				noeudP2_1.affiche();
				System.exit(0);
			}

			if(nouvelleprofondeurMaxP2>PROFONDEUR) {
				System.out.println("nouvelleprofondeurMaxP2_2:"+nouvelleprofondeurMaxP2);
				System.out.println("profondeurMinP2_2:"+profondeurMinP2_2);
				System.out.println("profondeurMaxP2_2:"+profondeurMaxP2_2);
				System.out.println("profondeurAleatoireP2_2:"+profondeurAleatoireP2_2);
				System.out.println("nombreNoeudP2_2:"+nombreNoeudP2_2);
				System.out.println("noeudAleatoireP2_2:"+noeudAleatoireP2_2);
				noeudP2_2.affiche();
				System.out.println("");
				System.out.println("nouvelleprofondeurMaxP1_2:"+nouvelleprofondeurMaxP1);
				System.out.println("profondeurMinP1_2:"+profondeurMinP1_2);
				System.out.println("profondeurMaxP1_2:"+profondeurMaxP1_2);
				System.out.println("profondeurAleatoireP1_2:"+profondeurAleatoireP1_2);
				System.out.println("nombreNoeudP1_2:"+nombreNoeudP1_2);
				System.out.println("noeudAleatoireP1_2:"+noeudAleatoireP1_2);
				noeudP1_2.affiche();

				System.exit(0);
			}
			
			
			
			
		}

	}

	public void croisementNonEquilibre() {
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
			int profondeurMaxP1 = vainqueur.get(0).getArbre().hauteur();
			int profondeurMaxP2 = vainqueur.get(1).getArbre().hauteur();

			Individu individuP1 = null;
			Individu individuP2 = null;

			if(profondeurMaxP1<=profondeurMaxP2) {
				// si P1 est plus petit alors on commence par lui 
				individuP1 = vainqueur.get(0).clone();
				individuP2 = vainqueur.get(1).clone();
			} else {
				// sinon on commence par P2
				individuP1 = vainqueur.get(1).clone();
				individuP2 = vainqueur.get(0).clone();
			}


			// INDIVIDU 1
			int profondeurMin = 0;
			int profondeurMax = individuP1.getArbre().hauteur();
			int profondeurAleatoire = (int) (profondeurMin + (Math.random() * (profondeurMax-profondeurMin))); // en 1 et la profondeur

			int nombreNoeud = individuP1.getArbre().nbNoeud(profondeurAleatoire);
			int noeudAleatoire = (int) (Math.random() * nombreNoeud);

			Noeud noeudP1 = individuP1.getArbre().getNoeud(profondeurAleatoire, noeudAleatoire);

			// INDIVIDU 2
			profondeurMin = 0;
			profondeurMax = profondeurAleatoire;
			//profondeurAleatoire = (int) (profondeurMin + (Math.random() * (profondeurMax-profondeurMin))); // en 1 et la profondeur

			nombreNoeud = individuP1.getArbre().nbNoeud(profondeurAleatoire);
			noeudAleatoire = (int) (Math.random() * nombreNoeud);

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
			if(pere1!=null) {
				pere1.changeFils(noeudP1, noeudP2);
			}

			if(pere2!=null) {
				pere2.changeFils(noeudP2, noeudP1);
			}

			// On ajoute à la nouvelle génération
			population.add(individuP1);
			population.add(individuP2);
		}
	}

	public void mutation() {
		for(int i=0 ; i<this.getNombreIndividu() ; i++) {
			int aleatoire = (int) (Math.random() * (NOMBREPOPULATION/(POURCENTAGEMUTATION*NOMBREPOPULATION)));

			if(aleatoire==0) {
				Individu individu = this.getIndividu(i);

				int profondeur = individu.getArbre().getProfondeur();
				int hauteur =  individu.getArbre().hauteur();
				if (hauteur!=PROFONDEUR) { 
					System.out.println("!!! arbre >10:"+hauteur); 
				}
				int profondeurAleatoire = (int) (1 + (Math.random() * (profondeur-1))); // en 1 et la profondeur
				Noeud nouveauNoeud = null;

				int test = profondeur-profondeurAleatoire;
				if (test == 1) {
					nouveauNoeud = new Feuille(null, Personnage.directionAleatoire());
				} else {
					Arbre arbre = new Arbre(individu.getMonde(), test);
					nouveauNoeud = arbre.getNoeud();
				}

				int nombreNoeud = individu.getArbre().nbNoeud(profondeurAleatoire);
				int noeudAleatoire = (int) (Math.random() * nombreNoeud);

				Noeud noeud = individu.getArbre().getNoeud(profondeurAleatoire, noeudAleatoire);
				Noeud pere = noeud.getPere();
				// Changement de pere
				//noeud.addPere(pere);
				nouveauNoeud.addPere(pere);
				// Changement de fils
				pere.changeFils(noeud, nouveauNoeud);
			}
		}
		//System.out.println("Nombre de muté: "+test);
	}

	public void mutationNonEquilibre() {
		for(int i=0 ; i<this.getNombreIndividu() ; i++) {
			int aleatoire = (int) (Math.random() * (NOMBREPOPULATION/(POURCENTAGEMUTATION*NOMBREPOPULATION)));

			if(aleatoire==0) {
				Individu individu = this.getIndividu(i);

				int profondeurMin = 0;
				int profondeurMax =  individu.getArbre().hauteur();
				if (profondeurMax>PROFONDEUR) { System.out.println("!!! arbre >10:"+profondeurMax); }


				int profondeurAleatoire = (int) (profondeurMin + (Math.random() * (profondeurMax-profondeurMin))); // en 1 et la profondeur
				Noeud nouveauNoeud = null;

				int tailleMin = 1;
				int tailleMax = profondeurMax-profondeurAleatoire;

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

					System.exit(0);
				}

			}
		}
		//System.out.println("Nombre de muté: "+test);
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

	public int[] lancerSelection() {
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

			int tmptab[] = new int[NOMBREPOPULATION];
			int index=0;
			for(Individu i: population) {
				tmptab[index] = i.getScore();
				index++;
			}

			/*for(Individu i: population) {
				if(!i.getArbre().equilibre()) {
					System.out.println("Arbre non equilibre");
				}
			}*/

			// Tout le monde à arrêté de jouer
			this.tournoi();
			
			this.croisement();
			//this.croisementNonEquilibre();
			//this.croisementNonEquilibreNonEquivalent();

			this.mutation();
			//this.mutationNonEquilibre();

			//this.ajoutVainqueur();

			nbGeneration++;
			aJoue = false;

			return tmptab;
		} else {
			System.out.println("La population n'a pas joué");
			return null;
		}
	}

	/*private void ajoutVainqueur() {
		population.addAll(vainqueur);
	}*/

	public void reinitialisation(Map map, Graphe graphe) {
		List<Individu> tmp = new ArrayList<Individu>();

		for(Individu i: population) {
			tmp.add(new Individu(map, graphe, i.getArbre(), 0));
		}

		population = tmp;
	}
}
