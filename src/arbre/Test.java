package arbre;

import model.Monde;

public abstract class Test extends Noeud {
	public Test(Noeud parent) {
		super(parent);
	}
	
	public Test(Noeud p, Noeud g, Noeud d) {
		super(p,g,d);
	}

	// GETTER
	public Noeud suivant(Monde monde) {
		if (test(monde)) {
			return this.getDroite();
		} else {
			return this.getGauche();
		}
	}

	public String toString() {
		return "Test";
	}

	// ABSTRACT
	public abstract boolean test(Monde monde);
	public abstract Test clone();
}
