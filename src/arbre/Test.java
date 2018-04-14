package arbre;

import model.Monde;

public abstract class Test extends Noeud {
	private Monde monde;

	public Test(Noeud parent, Monde monde) {
		super(parent);

		this.monde = monde;
	}

	// GETTER
	public Monde getMonde() {return monde;}
	public Noeud suivant() {
		if (test()) {
			return this.getDroite();
		} else {
			return this.getGauche();
		}
	}

	public String toString() {
		return "Test";
	}

	// ABSTRACT
	public abstract boolean test();
}
