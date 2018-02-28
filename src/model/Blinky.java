package model;

import java.util.ArrayList;

public class Blinky extends Personnage {
	Personnage pacMan;
	ArrayList<NoeudAStar> chemin;

	// Blinky attaque directement Pac Man. Il suit Pac-Man comme son ombre.
	public Blinky(int x, int y, Branche b, Personnage pm) {
		super(1,x,y,b);	
		this.invulnerable();
		pacMan =  pm;
		chemin = new ArrayList<NoeudAStar>();
	}

	// ABSTRACT
	public void manger() {

	}

	public int compare2Noeuds(NoeudAStar n1, NoeudAStar n2) {
		if (n1.getCoutH()<n2.getCoutH()) {
			return 1;
		} else if (n1.getCoutH()==n2.getCoutH()) {
			return 0;
		} else {
			return -1;
		}
	}

	public void decisionDirection() {

	}

	public void trouverChemin() {
		ArrayList<NoeudAStar> listeOuverte = new ArrayList<NoeudAStar>();
		ArrayList<NoeudAStar> listeFermee = new ArrayList<NoeudAStar>();

		// DESTINATION: elle ne change pas
		NoeudAStar arrivee = new NoeudAStar(pacMan.getNoeudDepart(), null);
		int arriveeX = arrivee.getX();
		int arriveeY = arrivee.getY();

		// DEPART: C'est le noeud courant 
		NoeudAStar courant = new NoeudAStar(this.getNoeudDestination(), null);
		NoeudAStar tmp;
		
		// On arrête pas tant qu'on est pas arrivé
		while(!courant.equals(arrivee)) {
			if (courant.existeHaut()) {
				tmp = new NoeudAStar(courant.enHaut(), courant);

				if(!listeFermee.equals(tmp)) {
					// Calcul de l'heuristique
					tmp.setCoutG();
					tmp.setCoutH(arriveeX, arriveeY);
					tmp.setCoutF();

					// en haut est présent dans la listeOuverte ? 
					int index = listeOuverte.indexOf(tmp);
					if (index!=-1) {
						// Si le nouveau est meilleur que l'ancien
						if(compare2Noeuds(tmp,listeOuverte.get(index))==1) {
							// On remplace l'ancien par le nouveau
							listeOuverte.set(index, tmp);							
						}
					} else {
						listeOuverte.add(tmp);
					}
				}
			}

			if (courant.aDroite()!=null) {
				tmp = new NoeudAStar(courant.aDroite(), courant);

				if(!listeFermee.equals(tmp)) {
					// Calcul de l'heuristique
					tmp.setCoutG();
					tmp.setCoutH(arriveeX, arriveeY);
					tmp.setCoutF();

					// en haut est présent dans la listeOuverte ? 
					int index = listeOuverte.indexOf(tmp);
					if (index!=-1) {
						// Si le nouveau est meilleur que l'ancien
						if(compare2Noeuds(tmp,listeOuverte.get(index))==1) {
							// On remplace l'ancien par le nouveau
							listeOuverte.set(index, tmp);							
						}
					} else {
						listeOuverte.add(tmp);
					}
				}
			}

			if (courant.enBas()!=null) {
				tmp = new NoeudAStar(courant.enBas(), courant);

				if(!listeFermee.equals(tmp)) {
					// Calcul de l'heuristique
					tmp.setCoutG();
					tmp.setCoutH(arriveeX, arriveeY);
					tmp.setCoutF();

					// en haut est présent dans la listeOuverte ? 
					int index = listeOuverte.indexOf(tmp);
					if (index!=-1) {
						// Si le nouveau est meilleur que l'ancien
						if(compare2Noeuds(tmp,listeOuverte.get(index))==1) {
							// On remplace l'ancien par le nouveau
							listeOuverte.set(index, tmp);							
						}
					} else {
						listeOuverte.add(tmp);
					}
				}
			}

			if (courant.aGauche()!=null) {
				tmp = new NoeudAStar(courant.aGauche(), courant);

				if(!listeFermee.equals(tmp)) {
					// Calcul de l'heuristique
					tmp.setCoutG();
					tmp.setCoutH(arriveeX, arriveeY);
					tmp.setCoutF();

					// en haut est présent dans la listeOuverte ? 
					int index = listeOuverte.indexOf(tmp);
					if (index!=-1) {
						// Si le nouveau est meilleur que l'ancien
						if(compare2Noeuds(tmp,listeOuverte.get(index))==1) {
							// On remplace l'ancien par le nouveau
							listeOuverte.set(index, tmp);							
						}
					} else {
						listeOuverte.add(tmp);
					}
				}
			}

			// ON CHERCHE LE MEILLEUR NOEUD DE LA LISTE OUVERTE
			int qualite = 99999;
			NoeudAStar meilleur = null;
			for (NoeudAStar nas: listeOuverte) {
				if (nas.getCoutF()<qualite) {
					qualite = nas.getCoutF();
					meilleur = nas;
				}
			}
			
			// LISTEOUVERTE VIDE
			if (meilleur==null) {
				System.err.println("ERREUR: Il n'y a pas de solution pour Blinky.");
				System.exit(0);
			}

			// ON L'AJOUTE A LA LISTE FERMEE
			listeFermee.add(meilleur);
			listeOuverte.remove(meilleur);

			// DEVIENT LE NOUVEAU NOEUD COURANT
			courant = meilleur;
		}
		
		chemin = listeFermee;
		
		for (NoeudAStar nas: listeFermee) {
			System.out.print("["+nas.getX()+","+nas.getY()+"] -> ");
		}
		System.out.println("");
		
		System.out.println("BRAVO TU AS TROUVE !");
	}
}
