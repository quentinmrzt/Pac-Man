package arbre;

import model.Monde;
import model.Personnage;

public class Arbre {
	private Noeud noeud;
	private int profondeur;
	private Monde monde;

	public Arbre(Monde monde, int profondeur) {
		this.monde = monde; 
		this.profondeur = profondeur;

		this.noeud = this.testAleatoire(null);
		generationAleatoirePrefixe(noeud,profondeur-1);
	}

	public Noeud getNoeud() { return noeud; }

	public void generationAleatoirePrefixe(Noeud noeud, int profondeur) {
		if (profondeur==1) {
			noeud.addGauche(new Feuille(noeud, Personnage.directionAleatoire()));
			noeud.addDroite(new Feuille(noeud, Personnage.directionAleatoire()));
		} else {
			noeud.addGauche(this.testAleatoire(noeud));
			noeud.addDroite(this.testAleatoire(noeud));

			generationAleatoirePrefixe(noeud.getGauche(),profondeur-1);
			generationAleatoirePrefixe(noeud.getDroite(),profondeur-1);
		}
	}

	public int getProfondeur() {return profondeur;}

	public int getDirection() {
		boolean fin = false;
		Noeud tmp = noeud;
		int direction = -1;

		while(!fin) {
			if(tmp instanceof Feuille) {
				direction = ((Feuille) tmp).getDirection();
				fin = true;
			}

			if(tmp instanceof Test) {
				tmp = ((Test) tmp).suivant();
			}

			if (tmp == null) {
				fin = true;
			}
		}

		return direction;
	}
	
	public Test testAleatoire(Noeud parent) {
		int rdm =  (int) (Math.random() * 2); 

		switch (rdm)
		{
		case 0:
			return new TestMur(parent, monde, Personnage.directionAleatoire());
		case 1:
			return new TestPacGomme(parent, monde, Personnage.directionAleatoire());
		case 2:
			return new TestEnnemi(parent, monde, Personnage.directionAleatoire());
		default:
			System.err.println("Erreur: test aléatoire.");
			return null;
		}
	}

	// Fonction basique
	public void parcoursPrefixe(Noeud noeud) {
		if(noeud!=null) {
			System.out.println("Noeud");

			this.parcoursPrefixe(noeud.getGauche());
			this.parcoursPrefixe(noeud.getDroite());
		}
	}

	public int hauteur() {
		return hauteur(noeud);
	}

	private int hauteur(Noeud noeud) {
		if (noeud == null) {
			return 0;
		} else {
			return 1 + max(hauteur(noeud.getGauche()),hauteur(noeud.getDroite()));
		}
	}

	private int max(int a, int b) {
		if (a>b) {
			return a;
		} else {
			return b;
		}
	}

	public int nbNoeud() {
		return nbNoeud(noeud);
	}

	private int nbNoeud(Noeud noeud) {
		if (noeud==null) {
			return 0;
		} else {
			return 1 + nbNoeud(noeud.getGauche()) + nbNoeud(noeud.getDroite());
		}
	}

	public void affiche(Noeud a, int profondeur) {
		if (a == null) {
			return;
		}

		for (int i = 0; i <= profondeur; i++) {
			System.out.print("|");
		}
		
		System.out.println(a.toString());
		affiche(a.getGauche(), profondeur+1);
		affiche(a.getDroite(), profondeur+1);
	}
}
