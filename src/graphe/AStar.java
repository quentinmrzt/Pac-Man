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
			arrivee = new NoeudAStar(p2.getNoeud());
		} else {
			arrivee = new NoeudAStar(new Noeud(p2.getPositionX(),p2.getPositionY()));
		}

		// DEPART
		if(p1.estSurNoeud()) {
			depart = new NoeudAStar(p1.getNoeud());
			courant = depart;
			listeFermee.add(depart);
		} else {
			// Si on est pas sur un noeud, on crée un faux noeud
			depart = new NoeudAStar(new Noeud(p1.getPositionX(),p1.getPositionY()));

			// On récupère les deux noeuds de sa branche
			NoeudAStar n1Depart, n2Depart;
			if(p1.getBranche().estHorizontal()) {
				n1Depart = new NoeudAStar(p1.getBranche().getN1(), depart, arrivee, Personnage.GAUCHE);
				n2Depart = new NoeudAStar(p1.getBranche().getN2(), depart, arrivee, Personnage.DROITE);
			} else {
				n1Depart = new NoeudAStar(p1.getBranche().getN1(), depart, arrivee, Personnage.HAUT);
				n2Depart = new NoeudAStar(p1.getBranche().getN2(), depart, arrivee, Personnage.BAS);
			}

			// et on determine lequel sera notre noeud courant
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

		NoeudAStar n1 = new NoeudAStar(p2.getBranche().getN1(), null, arrivee, Personnage.STATIQUE);
		NoeudAStar n2 = new NoeudAStar(p2.getBranche().getN2(), null, arrivee, Personnage.STATIQUE);

		// On arrête pas tant qu'on est pas arrivé
		while(!courant.equals(arrivee)) {
			System.out.println("1");
			// CAS DE LA FIN ---
			if(p2.getBranche().estHorizontal()) {
				if(courant.equals(n1)) {
					// DROITE
					arrivee.setParent(courant, Personnage.DROITE);
					listeOuverte.add(arrivee);
				} else if (courant.equals(n2)) {
					// GAUCHE
					arrivee.setParent(courant, Personnage.GAUCHE);
					listeOuverte.add(arrivee);
				}
			} else {
				if(courant.equals(n1)) {
					// BAS
					arrivee.setParent(courant, Personnage.BAS);
					listeOuverte.add(arrivee);
				} else if (courant.equals(n2)) {
					// HAUT
					arrivee.setParent(courant, Personnage.HAUT);
					listeOuverte.add(arrivee);

				}
			}
			// ---

			// HAUT
			if (courant.existeHaut() && courant.getDirection()!=Personnage.BAS) {
				tmp = new NoeudAStar(courant.enHaut(), courant, arrivee, Personnage.HAUT);
				listeOuverte.add(tmp);
			}
			// DROITE
			if (courant.existeDroite() && courant.getDirection()!=Personnage.GAUCHE) {
				tmp = new NoeudAStar(courant.aDroite(), courant, arrivee, Personnage.DROITE);
				listeOuverte.add(tmp);
			}
			// BAS
			if (courant.existeBas() && courant.getDirection()!=Personnage.HAUT) {
				tmp = new NoeudAStar(courant.enBas(), courant, arrivee, Personnage.BAS);
				listeOuverte.add(tmp);
			}
			// GAUCHE
			if (courant.existeGauche() && courant.getDirection()!=Personnage.DROITE) {
				tmp = new NoeudAStar(courant.aGauche(), courant, arrivee, Personnage.GAUCHE);
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
		if (courant.equals(n1) && !courant.equals(arrivee)) {
			if(p2.getBranche().estHorizontal()) {
				arrivee.setParent(courant,Personnage.DROITE);
			} else {
				arrivee.setParent(courant,Personnage.BAS);
			}
		} else if (courant.equals(n2) && !courant.equals(arrivee)) {
			if(p2.getBranche().estHorizontal()) {
				arrivee.setParent(courant,Personnage.GAUCHE);
			} else {
				arrivee.setParent(courant,Personnage.HAUT);
			}
		}
		listeFermee.add(arrivee);

		if (listeFermee.size()!=0) {
			NoeudAStar test = listeFermee.get(listeFermee.size()-1);

			// et on ajoute la liste fermée qui est notre chemin
			while (test.getParent()!=null) {
				System.out.println(test.getX()+"/"+test.getY());
				cheminDirection.add(test.getDirection());
				test = test.getParent();
			}
		}

		return cheminDirection;
	}
}
