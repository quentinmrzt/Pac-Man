package arbre;

import java.util.ArrayList;
import java.util.List;

import model.Monde;
import model.Personnage;

public class Arbre {
	private Noeud noeud;
	private int profondeur;
	public Monde monde;

	public Arbre() {
		this.monde = null;
		this.noeud = null;
		this.profondeur = 0;
	}

	public Arbre(Monde monde, int profondeur) {
		this.monde = monde; 
		this.profondeur = profondeur;

		if (profondeur==1) {
			this.noeud = new Feuille(null, Personnage.directionAleatoire());
		} else {
			this.noeud = this.testAleatoire(null);
			generationAleatoireEquilibre(noeud,profondeur-1);
			//generationAleatoireNonEquilibre(noeud,profondeur-1);
		}
	}

	public Arbre(Arbre arbre, Monde monde) {
		this.monde = monde;
		this.profondeur = arbre.getProfondeur();
		this.noeud = arbre.getNoeud();
	}

	public Arbre clone() {
		Arbre clone = new Arbre();

		clone.setMonde(this.monde);
		clone.setProfondeur(this.profondeur);
		clone.setNoeud(this.noeud.clone());

		return clone;
	}

	public Noeud getNoeud() { return noeud; }
	public int getProfondeur() {return profondeur;}

	private void setMonde(Monde monde) { this.monde = monde; }
	private void setProfondeur(int profondeur) { this.profondeur = profondeur; }
	public void setNoeud(Noeud noeud) { this.noeud = noeud; }

	private void generationAleatoireEquilibre(Noeud noeud, int profondeur) {
		if (profondeur==1) {
			noeud.addGauche(new Feuille(noeud, Personnage.directionAleatoire()));
			noeud.addDroite(new Feuille(noeud, Personnage.directionAleatoire()));
		} else {
			noeud.addGauche(this.testAleatoire(noeud));
			noeud.addDroite(this.testAleatoire(noeud));

			generationAleatoireEquilibre(noeud.getGauche(),profondeur-1);
			generationAleatoireEquilibre(noeud.getDroite(),profondeur-1);
		}
	}

	private void generationAleatoireNonEquilibre(Noeud noeud, int profondeur) {
		if (profondeur==1) {
			noeud.addGauche(new Feuille(noeud, Personnage.directionAleatoire()));
			noeud.addDroite(new Feuille(noeud, Personnage.directionAleatoire()));
		} else {
			// plus on est profond, plus on risque de s'arrêter
			int min = 1;
			int max = profondeur;
			int aleatoireGauche = (int) (min + (Math.random() * (max-min)));
			int aleatoireDroite= (int) (min + (Math.random() * (max-min)));

			if(aleatoireGauche!=1) {
				noeud.addGauche(this.testAleatoire(noeud));
				generationAleatoireNonEquilibre(noeud.getGauche(),profondeur-1);
			} else {
				noeud.addGauche(new Feuille(noeud, Personnage.directionAleatoire()));
			}
	
			if(aleatoireDroite!=1) {
				noeud.addDroite(this.testAleatoire(noeud));
				generationAleatoireNonEquilibre(noeud.getDroite(),profondeur-1);
			} else {
				noeud.addDroite(new Feuille(noeud, Personnage.directionAleatoire()));
			}
		}
	}

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
				tmp = ((Test) tmp).suivant(monde);
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
			return new TestMur(parent, Personnage.directionAleatoire());
		case 1:
			return new TestPacGomme(parent, Personnage.directionAleatoire());
		case 2:
			return new TestEnnemi(parent, Personnage.directionAleatoire());
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

	public boolean equilibre() {
		return equilibre(noeud);
	}

	private boolean equilibre(Noeud noeud) {
		if (noeud==null) {
			return true;
		}

		int tailleGauche = hauteur(noeud.getGauche());
		int tailleDroite = hauteur(noeud.getDroite());

		if (tailleGauche!=tailleDroite) {
			return false;
		} else {
			return equilibre(noeud.getGauche()) && equilibre(noeud.getDroite());
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

	public void affiche() {
		this.affiche(noeud,0);
	}

	private void affiche(Noeud a, int profondeur) {
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

	/**
	 * Donne le nombre de noeud existant à une profondeur donnée
	 * @param profondeur La profondeur voulu
	 * @return Le nombre de noeud qui existe
	 */
	public int nbNoeud(int profondeur) {
		return nbNoeud(noeud,profondeur);
	}

	private int nbNoeud(Noeud noeud,int profondeur) {
		if (noeud==null) {
			return 0;
		} else {
			if (profondeur==0) {
				return 1;
			} else {
				return nbNoeud(noeud.getGauche(),profondeur-1) + nbNoeud(noeud.getDroite(),profondeur-1);
			}
		}
	}

	/**
	 * Donne le noeud à une profondeur et un index voulu
	 * @param profondeur La profondeur dans l'arbre
	 * @param index Le numéro du noeud
	 * @return Le noeud voulu
	 */
	public Noeud getNoeud(int profondeur, int index) {
		List<Noeud> liste = new ArrayList<Noeud>();
		getNoeud(noeud,liste, profondeur);

		if(liste.size()<=index) {
			System.out.println("ERREUR --------");
		}
		
		return liste.get(index);
	}

	private void getNoeud(Noeud noeud, List<Noeud> liste ,int profondeur) {
		if (noeud!=null) {
			if (profondeur==0) {
				liste.add(noeud);
			} else {
				getNoeud(noeud.getGauche(),liste, profondeur-1);
				getNoeud(noeud.getDroite(),liste, profondeur-1);
			}
		}		
	}

	/*public static void main(String[] args) {
		Arbre arbre = new Arbre(null,3);
		arbre.affiche();
		System.out.println("Arbre equilibre="+arbre.equilibre());
		System.out.println("");

		Arbre arbreNouveau = new Arbre(null,2);
		arbreNouveau.affiche();
		System.out.println("");

		arbre.getNoeud(1, 0).addDroite(arbreNouveau.getNoeud());
		arbre.affiche();
		System.out.println("Arbre equilibre="+arbre.equilibre());
	}*/
}
