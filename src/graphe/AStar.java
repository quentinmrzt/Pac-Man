package graphe;

import java.util.ArrayList;

import model.Personnage;

public class AStar {
	private static ArrayList<NoeudAStar> listeOuverte = new ArrayList<NoeudAStar>();
	private static ArrayList<NoeudAStar> listeFermee = new ArrayList<NoeudAStar>();

	private static void gestionDesListes(NoeudAStar noeudDepart, NoeudAStar noeudArrivee) {
		if(listeFermee.indexOf(noeudDepart)==-1) {
			// Calcul de l'heuristique
			noeudDepart.setCoutG(listeFermee.get(listeFermee.size()-1));
			noeudDepart.setCoutH(noeudArrivee);
			noeudDepart.setCoutF();
			
			listeOuverte.add(noeudDepart);
		}
	}

	public static ArrayList<Integer> trouverChemin(Noeud noeudDepart, Noeud noeudArrivee) {
		listeOuverte.clear();
		listeFermee.clear();
		ArrayList<Integer> cheminDirection = new ArrayList<Integer>();
		
		// DESTINATION
		NoeudAStar arrivee = new NoeudAStar(noeudArrivee, null, Personnage.STATIQUE);
		
		// DEPART
		NoeudAStar courant =  new NoeudAStar(noeudDepart, null, Personnage.STATIQUE);	
		listeFermee.add(courant);
				
		// On arrête pas tant qu'on est pas arrivé
		while(!courant.equals(arrivee)) {
			// HAUT
			if (courant.existeHaut() && courant.getDirection()!=Personnage.BAS) {
				NoeudAStar tmp = new NoeudAStar(courant.enHaut(), courant, Personnage.HAUT);
				gestionDesListes(tmp,arrivee);
			}
			// DROITE
			if (courant.existeDroite() && courant.getDirection()!=Personnage.GAUCHE) {
				NoeudAStar tmp = new NoeudAStar(courant.aDroite(), courant, Personnage.DROITE);
				gestionDesListes(tmp,arrivee);
			}
			// BAS
			if (courant.existeBas() && courant.getDirection()!=Personnage.HAUT) {
				NoeudAStar tmp = new NoeudAStar(courant.enBas(), courant, Personnage.BAS);
				gestionDesListes(tmp,arrivee);
			}
			// GAUCHE
			if (courant.existeGauche() && courant.getDirection()!=Personnage.DROITE) {
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
			while (test!=null) {
				cheminDirection.add(test.getDirection());
				test = test.getParent();
			}
		}

		return cheminDirection;
	}
}
