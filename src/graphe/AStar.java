package graphe;

import java.util.ArrayList;

import model.Personnage;

public class AStar {
	public static ArrayList<Integer> trouverChemin(Personnage p1, Personnage p2) {
		ArrayList<NoeudAStar> listeOuverte = new ArrayList<NoeudAStar>();
		ArrayList<NoeudAStar> listeFermee = new ArrayList<NoeudAStar>();
		ArrayList<Integer> cheminDirection = new ArrayList<Integer>();
		NoeudAStar tmp, depart, arrivee, courant;
		
		// ARRIVEE
		if(p2.estSurNoeud()) {
			arrivee = new NoeudAStar(p2.getNoeud(), null, null);
		} else {
			arrivee = new NoeudAStar(new Noeud(p2.getPositionX(),p2.getPositionY()), null, null);
		}
		NoeudAStar n1Arrivee = new NoeudAStar(p2.getBranche().getN1(), null, arrivee);
		NoeudAStar n2Arrivee = new NoeudAStar(p2.getBranche().getN2(), null, arrivee);

		// DEPART
		if(p1.estSurNoeud()) {
			depart = new NoeudAStar(p1.getNoeud(),null,arrivee);
			courant = depart;
			listeFermee.add(depart);
		} else {
			// Si on est pas sur un noeud, on crée un faux noeud
			depart = new NoeudAStar(new Noeud(p1.getPositionX(),p1.getPositionY()), null, arrivee);
			// On récupère les deux noeuds de sa branche
			NoeudAStar n1Depart = new NoeudAStar(p1.getBranche().getN1(), depart, arrivee);
			NoeudAStar n2Depart = new NoeudAStar(p1.getBranche().getN2(), depart, arrivee);

			// notre noeud courant est le meilleur
			if(n1Depart.getHeuristique()<=n2Depart.getHeuristique()) {
				courant = n1Depart;
				listeFermee.add(n1Depart);
				listeOuverte.add(n2Depart);
			} else {
				courant = n2Depart;
				listeFermee.add(n2Depart);
				listeOuverte.add(n1Depart);
			}
		}

		// On arrête pas tant qu'on est pas arrivé
		while(!courant.equals(arrivee)) {
			// Si on arrive sur la branche finale
			if(courant.equals(n1Arrivee) || courant.equals(n2Arrivee)) {
				arrivee.setParent(courant);
				listeOuverte.add(arrivee);
			}

			// HAUT
			if (courant.existeHaut() && courant.getDirection()!=Personnage.BAS) {
				tmp = new NoeudAStar(courant.enHaut(), courant, arrivee);
				listeOuverte.add(tmp);
			}
			// DROITE
			if (courant.existeDroite() && courant.getDirection()!=Personnage.GAUCHE) {
				tmp = new NoeudAStar(courant.aDroite(), courant, arrivee);
				listeOuverte.add(tmp);
			}
			// BAS
			if (courant.existeBas() && courant.getDirection()!=Personnage.HAUT) {
				tmp = new NoeudAStar(courant.enBas(), courant, arrivee);
				listeOuverte.add(tmp);
			}
			// GAUCHE
			if (courant.existeGauche() && courant.getDirection()!=Personnage.DROITE) {
				tmp = new NoeudAStar(courant.aGauche(), courant, arrivee);
				listeOuverte.add(tmp);
			}

			// ON CHERCHE LE MEILLEUR NOEUD DE LA LISTE OUVERTE
			int qualite = 99999; 
			int index = -1;
			int i = 0;
			for (NoeudAStar nas: listeOuverte) {
				if (nas.getHeuristique()<qualite) {
					qualite = nas.getHeuristique();
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

		// Il nous manque la dernière étape, pour attraper pacMan
		if (courant.equals(n1Arrivee) && !courant.equals(arrivee)) {
			arrivee.setParent(courant);
		}
		listeFermee.add(arrivee);

		if (listeFermee.size()!=0) {
			NoeudAStar test = listeFermee.get(listeFermee.size()-1);

			// et on ajoute la liste fermée qui est notre chemin
			while (test.getParent()!=null) {
				//System.out.println(test.getX()+"/"+test.getY());
				cheminDirection.add(test.getDirection());
				test = test.getParent();
			}
		}

		return cheminDirection;
	}
}
