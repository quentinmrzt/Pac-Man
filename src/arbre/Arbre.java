package arbre;

public class Arbre {
	private int taille=0;
	private Noeud noeud;
	
	public Arbre(Noeud noeud) {
		this.noeud = noeud;
	}
	
	public int getTailel() {return taille;}
	
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
}
