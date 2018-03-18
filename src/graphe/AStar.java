package graphe;

import java.util.ArrayList;

import model.Personnage;

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
				System.out.print("(P");
				// Si le nouveau est meilleur que l'ancien
				if(compare2Noeuds(noeudDepart,listeOuverte.get(index))==1) {
					// On remplace l'ancien par le nouveau
					listeOuverte.set(index, noeudDepart);
					System.out.print("M) ");
				} else {
					System.out.print("PM) ");
				}
			} else {
				listeOuverte.add(noeudDepart);
			}
		}
	}

	public static ArrayList<NoeudAStar> trouverChemin(Noeud noeudDepart, Noeud noeudArrivee) {
		System.out.println("ASTAR: On cherche un chemin");
		listeOuverte.clear();
		listeFermee.clear();
		chemin.clear();
		
		ArrayList<Integer> listeDirection = new ArrayList<Integer>();

		// DESTINATION
		NoeudAStar arrivee = new NoeudAStar(noeudArrivee, null, Personnage.STATIQUE);
		
		// DEPART
		NoeudAStar courant =  new NoeudAStar(noeudDepart, null, Personnage.STATIQUE);	
		
		int nbTour = 1;
		// On arrête pas tant qu'on est pas arrivé
		while(!courant.equals(arrivee)) {
			System.out.println("Tour n°"+nbTour+": x("+courant.getX()+") / y("+courant.getY()+")");
			System.out.print(" ");
			// HAUT
			if (courant.existeHaut()) {
				System.out.print("HAUT ");
				NoeudAStar tmp = new NoeudAStar(courant.enHaut(), courant, Personnage.HAUT);
				gestionDesListes(tmp,arrivee);
			}
			// DROITE
			if (courant.existeDroite()) {
				System.out.print("DROITE ");
				NoeudAStar tmp = new NoeudAStar(courant.aDroite(), courant, Personnage.DROITE);
				gestionDesListes(tmp,arrivee);
			}
			// BAS
			if (courant.existeBas()) {
				System.out.print("BAS ");
				NoeudAStar tmp = new NoeudAStar(courant.enBas(), courant, Personnage.BAS);
				gestionDesListes(tmp,arrivee);
			}
			// GAUCHE
			if (courant.existeGauche()) {
				System.out.print("GAUCHE ");
				NoeudAStar tmp = new NoeudAStar(courant.aGauche(), courant, Personnage.GAUCHE);
				gestionDesListes(tmp,arrivee);
			}
			System.out.println("");
			
			System.out.print(" Liste ouverte: ");
			for (NoeudAStar nas: listeOuverte) {
				System.out.print("("+nas.getX()+"/"+nas.getY()+"|"+nas.getCoutF()+") ");
			}
			System.out.println("");
			
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
			
			System.out.print(" Liste fermée: ");
			for (NoeudAStar nas: listeFermee) {
				System.out.print("("+nas.getX()+"/"+nas.getY()+"|"+nas.getCoutF()+") ");
			}
			System.out.println("");
			
			nbTour++;
		}

		if (listeFermee.size()!=0) {
			NoeudAStar test = listeFermee.get(listeFermee.size()-1);

			// et on ajoute la liste fermée qui est notre chemin
			while (test.getParent()!=null) {
				chemin.add(test);
				test = test.getParent();
			}
		}

		System.out.println("");
		return chemin;
	}
}
