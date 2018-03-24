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

	public static ArrayList<Integer> trouverChemin(Personnage p1, Personnage p2) {
		// DEPART
		Branche brancheDepart = new Branche(p1.getBranche());
		NoeudAStar depart;
		
		if(p1.getNoeud()==null) {
			// Vu qu'on est pas sur un noeud, on crée un faux noeud à la place du personnage
			System.out.print("Le personnage depart n'est pas sur un noeud: ");
			Noeud tmp = new Noeud(p1.getPositionX(),p1.getPositionY());
			if(brancheDepart.estHorizontal()) {
				tmp.setDroite(brancheDepart.getN2());
				tmp.setGauche(brancheDepart.getN1());
			} else {
				tmp.setHaut(brancheDepart.getN1());
				tmp.setBas(brancheDepart.getN2());
			}
			
			depart = new NoeudAStar(tmp,null,Personnage.STATIQUE);
		} else {
			System.out.print("Le personnage depart est sur un noeud: ");
			depart = new NoeudAStar(p1.getNoeud(),null,Personnage.STATIQUE);
		}
		
		System.out.println("X:"+depart.getX()+" et Y:"+depart.getY());
		
		// ARRIVEE
		Branche brancheArrivee = new Branche(p2.getBranche());
		NoeudAStar arrivee;
		
		if(p2.getNoeud()==null) {
			// Vu qu'on est pas sur un noeud, on crée un faux noeud à la place du personnage
			System.out.print("Le personnage arivée n'est pas sur un noeud: ");
			Noeud tmp = new Noeud(p2.getPositionX(),p2.getPositionY());
			if(brancheArrivee.estHorizontal()) {
				tmp.setDroite(brancheArrivee.getN2());
				tmp.setGauche(brancheArrivee.getN1());
				brancheArrivee.getN1().setDroite(tmp);
				brancheArrivee.getN2().setGauche(tmp);
			} else {
				tmp.setHaut(brancheArrivee.getN1());
				tmp.setBas(brancheArrivee.getN2());
				brancheArrivee.getN2().setHaut(tmp);
				brancheArrivee.getN1().setBas(tmp);
			}
			
			arrivee = new NoeudAStar(tmp,null,Personnage.STATIQUE);
		} else {
			System.out.print("Le personnage depart est sur un noeud: ");
			arrivee = new NoeudAStar(p2.getNoeud(),null,Personnage.STATIQUE);
		}
		
		System.out.println("X:"+arrivee.getX()+" et Y:"+arrivee.getY());
		
		// ****************************************************************************
		
		listeOuverte.clear();
		listeFermee.clear();
		ArrayList<Integer> cheminDirection = new ArrayList<Integer>();
				
		// DEPART
		NoeudAStar courant = depart;
		listeFermee.add(courant);
				
		// On arrête pas tant qu'on est pas arrivé
		while(!courant.equals(arrivee)) {
			if (courant.getX()==brancheArrivee.getN1().getX() && courant.getY()==brancheArrivee.getN1().getY()) {
				System.out.println("On est arrivé en N1!");
			}
			
			if (courant.getX()==brancheArrivee.getN2().getX() && courant.getY()==brancheArrivee.getN2().getY()) {
				System.out.println("On est arrivé en N2!");
			}
			
			System.out.print("Début du tour: ("+courant.getX()+"/"+courant.getY()+") (");
			// HAUT
			if (courant.existeHaut() && courant.getDirection()!=Personnage.BAS) {
				System.out.print("HAUT ");
				NoeudAStar tmp = new NoeudAStar(courant.enHaut(), courant, Personnage.HAUT);
				gestionDesListes(tmp,arrivee);
			}
			// DROITE
			if (courant.existeDroite() && courant.getDirection()!=Personnage.GAUCHE) {
				System.out.print("DROITE ");
				NoeudAStar tmp = new NoeudAStar(courant.aDroite(), courant, Personnage.DROITE);
				gestionDesListes(tmp,arrivee);
			}
			// BAS
			if (courant.existeBas() && courant.getDirection()!=Personnage.HAUT) {
				System.out.print("BAS ");
				NoeudAStar tmp = new NoeudAStar(courant.enBas(), courant, Personnage.BAS);
				gestionDesListes(tmp,arrivee);
			}
			// GAUCHE
			if (courant.existeGauche() && courant.getDirection()!=Personnage.DROITE) {
				System.out.print("GAUCHE ");
				NoeudAStar tmp = new NoeudAStar(courant.aGauche(), courant, Personnage.GAUCHE);
				gestionDesListes(tmp,arrivee);
			}
			
			System.out.println(")");

			
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
