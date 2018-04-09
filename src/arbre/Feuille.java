package arbre;

public class Feuille extends Noeud {
	private int direction;
	private String str;
	
	public Feuille(Noeud parent, int direction) {
		super(parent);
		this.direction = direction;
	}
	
	public int getDirection() {return direction;}
	public String getStr() {return str;}
}
