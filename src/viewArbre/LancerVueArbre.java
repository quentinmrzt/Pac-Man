package viewArbre;

import java.util.ArrayList;
import java.util.List;

import arbre.Arbre;

public class LancerVueArbre {
	public static void main(String[] args) {
		List<Arbre> arbres = new ArrayList<Arbre>();
		
		for (int i=0 ; i<100 ; i++) {
			arbres.add(new Arbre(null, 4));
		}

		new FenetreArbres(arbres);
		//new Fenetre(arbre);
	}
}
