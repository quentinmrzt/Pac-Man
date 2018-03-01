package model;

import java.util.ArrayList;

public class AStar {
	private static ArrayList<NoeudAStar> listeOuverte = new ArrayList<NoeudAStar>();
	private static ArrayList<NoeudAStar> listeFermee = new ArrayList<NoeudAStar>();
	private static ArrayList<NoeudAStar> chemin = new ArrayList<NoeudAStar>();

	private static int compare2Noeuds(NoeudAStar n1, NoeudAStar n2) {
		if (n1.getCoutH()<n2.getCoutH()) {
			return 1;
		} else if (n1.getCoutH()==n2.getCoutH()) {
			return 0;
		} else {
			return -1;
		}
	}

	private static void gestionDesListes(NoeudAStar noeudDepart, NoeudAStar noeudArrivee) {
		if(listeFermee.indexOf(noeudDepart)==-1) {
			// Calcul de l'heuristique
			noeudDepart.setCoutG();
			noeudDepart.setCoutH(noeudArrivee.getX(), noeudArrivee.getY());
			noeudDepart.setCoutF();

			// en haut est présent dans la listeOuverte ? 
			int index = listeOuverte.indexOf(noeudDepart);
			if (index!=-1) {
				// Si le nouveau est meilleur que l'ancien
				if(compare2Noeuds(noeudDepart,listeOuverte.get(index))==1) {
					// On remplace l'ancien par le nouveau
					listeOuverte.set(index, noeudDepart);						
				}
			} else {
				listeOuverte.add(noeudDepart);
			}

			listeOuverte.add(noeudDepart);
		}
	}

	public static ArrayList<NoeudAStar> trouverChemin(Noeud noeudDepart, Noeud noeudArrivee) {
		listeOuverte.clear();
		listeFermee.clear();
		chemin.clear();

		// DESTINATION
		NoeudAStar arrivee = new NoeudAStar(noeudArrivee, null, Personnage.STATIQUE);

		// DEPART
		NoeudAStar courant =  new NoeudAStar(noeudDepart, null, Personnage.STATIQUE);;		

		// On arrête pas tant qu'on est pas arrivé
		while(!courant.equals(arrivee)) {
			// HAUT
			if (courant.existeHaut()) {
				NoeudAStar tmp = new NoeudAStar(courant.enHaut(), courant, Personnage.HAUT);
				gestionDesListes(tmp,arrivee);
			}
			// DROITE
			if (courant.existeDroite()) {
				NoeudAStar tmp = new NoeudAStar(courant.aDroite(), courant, Personnage.DROITE);
				gestionDesListes(tmp,arrivee);
			}
			// BAS
			if (courant.existeBas()) {
				NoeudAStar tmp = new NoeudAStar(courant.enBas(), courant, Personnage.BAS);
				gestionDesListes(tmp,arrivee);
			}
			// GAUCHE
			if (courant.existeGauche()) {
				NoeudAStar tmp = new NoeudAStar(courant.aGauche(), courant, Personnage.GAUCHE);
				gestionDesListes(tmp,arrivee);
			}

			// ON CHERCHE LE MEILLEUR NOEUD DE LA LISTE OUVERTE
			int qualite = 99999; 
			int index = -1;
			int i = 0;
			for (NoeudAStar nas: listeOuverte) {
				if (nas.getCoutF()<qualite) {
					qualite = nas.getCoutF();
					index = i;
				}
				i++;
			}

			// LISTEOUVERTE VIDE
			if (index==-1) {
				System.err.println("ERREUR: Il n'y a pas de solution pour Blinky.");
				System.exit(0);
			}

			// Liste fermee + courant
			listeFermee.add(listeOuverte.get(index));
			courant = listeOuverte.get(index);
			listeOuverte.remove(index);
		}

		if (listeFermee.size()!=0) {
			NoeudAStar test = listeFermee.get(listeFermee.size()-1);

			// et on ajoute la liste fermée qui est notre chemin
			while (test.getParent()!=null) {
				chemin.add(test);
				test = test.getParent();
			}
		}

		return chemin;
	}
}
