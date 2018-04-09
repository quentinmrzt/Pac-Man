package aStar;

import java.util.ArrayList;

import graphe.Noeud;
import model.Personnage;

public class AStar {
	/**
	 * Permet de trouver un chemin entre deux personnages
	 * @param p1 Le personnage qui cherche
	 * @param p2 Le personnage qui est cherché 
	 * @return Une liste de direction
	 */
	public static ArrayList<Integer> trouverCheminPersonnage(Personnage p1, Personnage p2) {
		ArrayList<Integer> chemin;
		int premiereDirection = Personnage.STATIQUE, derniereDirection = Personnage.STATIQUE;

		if (p1.estSurNoeud() && p2.estSurNoeud()) {
			return trouverCheminNoeud(p1.getNoeud(),p2.getNoeud());
		} else if(p1.estSurNoeud()) {
			Noeud noeudP2;

			if (p2.getBranche().estHorizontal()) {
				if (p2.getPositionX()-p2.getBranche().getN1().getX() <= p2.getBranche().getN2().getX()-p2.getPositionX()) {
					noeudP2 = p2.getBranche().getN1();
					derniereDirection = Personnage.DROITE;
				} else {
					noeudP2 = p2.getBranche().getN2();
					derniereDirection = Personnage.GAUCHE;
				}
			} else {
				if (p2.getPositionY()-p2.getBranche().getN1().getY() <= p2.getBranche().getN2().getY()-p2.getPositionY()) {
					noeudP2 = p2.getBranche().getN1();
					derniereDirection = Personnage.BAS;
				} else {
					noeudP2 = p2.getBranche().getN2();
					derniereDirection = Personnage.HAUT;
				}
			}

			chemin = trouverCheminNoeud(p1.getNoeud(),noeudP2);
		} else if (p2.estSurNoeud()) {
			Noeud noeudP1;

			if (p1.getBranche().estHorizontal()) {
				if (p1.getPositionX()-p1.getBranche().getN1().getX() <= p1.getBranche().getN2().getX()-p1.getPositionX()) {
					noeudP1 = p1.getBranche().getN1();
					premiereDirection = Personnage.GAUCHE;
				} else {
					noeudP1 = p1.getBranche().getN2();
					premiereDirection = Personnage.DROITE;
				}
			} else {
				if (p1.getPositionY()-p1.getBranche().getN1().getY() <= p1.getBranche().getN2().getY()-p1.getPositionY()) {
					noeudP1 = p1.getBranche().getN1();
					premiereDirection = Personnage.HAUT;
				} else {
					noeudP1 = p1.getBranche().getN2();
					premiereDirection = Personnage.BAS;
				}
			}

			chemin = trouverCheminNoeud(noeudP1,p2.getNoeud());
		} else {
			// PERSONNAGE 1
			Noeud noeudP1;
			if (p1.getBranche().estHorizontal()) {
				if (p1.getPositionX()-p1.getBranche().getN1().getX() <= p1.getBranche().getN2().getX()-p1.getPositionX()) {
					noeudP1 = p1.getBranche().getN1();
					premiereDirection = Personnage.GAUCHE;
				} else {
					noeudP1 = p1.getBranche().getN2();
					premiereDirection = Personnage.DROITE;
				}
			} else {
				if (p1.getPositionY()-p1.getBranche().getN1().getY() <= p1.getBranche().getN2().getY()-p1.getPositionY()) {
					noeudP1 = p1.getBranche().getN1();
					premiereDirection = Personnage.HAUT;
				} else {
					noeudP1 = p1.getBranche().getN2();
					premiereDirection = Personnage.BAS;
				}
			}

			// PERSONANGE 2
			Noeud noeudP2;
			if (p2.getBranche().estHorizontal()) {
				if (p2.getPositionX()-p2.getBranche().getN1().getX() <= p2.getBranche().getN2().getX()-p2.getPositionX()) {
					noeudP2 = p2.getBranche().getN1();
					derniereDirection = Personnage.DROITE;
				} else {
					noeudP2 = p2.getBranche().getN2();
					derniereDirection = Personnage.GAUCHE;
				}
			} else {
				if (p2.getPositionY()-p2.getBranche().getN1().getY() <= p2.getBranche().getN2().getY()-p2.getPositionY()) {
					noeudP2 = p2.getBranche().getN1();
					derniereDirection = Personnage.BAS;
				} else {
					noeudP2 = p2.getBranche().getN2();
					derniereDirection = Personnage.HAUT;
				}
			}

			chemin = trouverCheminNoeud(noeudP1,noeudP2);
		}

		// Vue qu'on n'est pas partit du noeud, on rajoute la direction qui mene à celui ci
		if (chemin.size()>0) {
			if(premiereDirection!=Personnage.STATIQUE && chemin.get(chemin.size()-1) != Personnage.directionInverse(premiereDirection)) {
				chemin.add(premiereDirection);
			}

			if(derniereDirection!=Personnage.STATIQUE && chemin.get(0) != Personnage.directionInverse(derniereDirection)) {
				chemin.add(0, derniereDirection);
			}
		}

		return chemin;
	}
	
	public static ArrayList<Integer> trouverCheminPersonnage(Personnage p1, Noeud n2) {
		ArrayList<Integer> chemin;
		int premiereDirection = Personnage.STATIQUE;

		if(p1.estSurNoeud()) {
			return trouverCheminNoeud(p1.getNoeud(),n2);
		} else {
			Noeud noeudP1;

			if (p1.getBranche().estHorizontal()) {
				if (p1.getPositionX()-p1.getBranche().getN1().getX() <= p1.getBranche().getN2().getX()-p1.getPositionX()) {
					noeudP1 = p1.getBranche().getN1();
					premiereDirection = Personnage.GAUCHE;
				} else {
					noeudP1 = p1.getBranche().getN2();
					premiereDirection = Personnage.DROITE;
				}
			} else {
				if (p1.getPositionY()-p1.getBranche().getN1().getY() <= p1.getBranche().getN2().getY()-p1.getPositionY()) {
					noeudP1 = p1.getBranche().getN1();
					premiereDirection = Personnage.HAUT;
				} else {
					noeudP1 = p1.getBranche().getN2();
					premiereDirection = Personnage.BAS;
				}
			}

			chemin = trouverCheminNoeud(noeudP1,n2);
		}
		
		// Vue qu'on n'est pas partit du noeud, on rajoute la direction qui mene à celui ci
		if (chemin.size()>0) {
			if(premiereDirection!=Personnage.STATIQUE && chemin.get(chemin.size()-1) != Personnage.directionInverse(premiereDirection)) {
				chemin.add(premiereDirection);
			}
		}

		return chemin;
	}

	public static ArrayList<Integer> trouverCheminNoeud(Noeud n1, Noeud n2) {
		ArrayList<NoeudAStar> listeOuverte = new ArrayList<NoeudAStar>();
		ArrayList<NoeudAStar> listeFermee = new ArrayList<NoeudAStar>();
		ArrayList<Integer> cheminDirection = new ArrayList<Integer>();
		NoeudAStar tmp, depart, arrivee, courant;

		// ARRIVEE
		arrivee = new NoeudAStar(n2, null, null);

		// DEPART
		depart = new NoeudAStar(n1,null,arrivee);
		courant = depart;
		listeFermee.add(depart);


		// On arrête pas tant qu'on est pas arrivé
		while(!courant.equals(arrivee)) {
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

		if (listeFermee.size()!=0) {
			NoeudAStar test = listeFermee.get(listeFermee.size()-1);

			// et on ajoute la liste fermée qui est notre chemin
			while (test.getParent()!=null) {
				cheminDirection.add(test.getDirection());
				test = test.getParent();
			}
		}

		return cheminDirection;
	}
}
