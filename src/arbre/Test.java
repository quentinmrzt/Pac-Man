package arbre;

import model.Modelisation;

public abstract class Test extends Noeud {
	private Modelisation modelisation;

	public Test(Noeud parent, Modelisation modelisation) {
		super(parent);
		
		this.modelisation = modelisation;
	}
	
	// GETTEr
	public Modelisation getModelisation() {return modelisation;}
	public Noeud suivant() {
		if (test()) {
			return this.getDroite();
		} else {
			return this.getGauche();
		}
	}
	
	// ABSTRACT
	public abstract boolean test();
}
