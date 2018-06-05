package evolution;

import arbre.Arbre;

public class Information {
	private Arbre arbre;
	private int score;
	private boolean tournoi, vainqueur, mute;

	public Information() {
		this.arbre = null;
		this.score = 0;
		this.tournoi = true;
		this.vainqueur = false;
		this.mute = false;
	}

	public void setArbre(Arbre arbre) { this.arbre = arbre; }
	public void setScore(int score) { this.score = score; }
	public void setPasTournoi() { this.tournoi = false; }
	public void setEstVainqueur() { this.vainqueur = true; }

	public Arbre getArbre() { return arbre; }
	public int getScore() { return score; }
	public boolean estTournoi() { return tournoi; }
	public boolean estVainqueur() { return vainqueur; }
	public boolean estMute() { return mute; }
	
	
}
