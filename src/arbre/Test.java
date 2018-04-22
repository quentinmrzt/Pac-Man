package arbre;

import model.Monde;

public abstract class Test extends Noeud {

	public Test(Noeud parent) {
		super(parent);
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
}
