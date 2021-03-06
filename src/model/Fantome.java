package model;

import java.util.ArrayList;

import graphe.Branche;

public abstract class Fantome extends Personnage {
	private Personnage pacMan;
	private ArrayList<Integer> chemin;
	private int datePrison;
	private boolean invulnerable;
	private int tourVulnerabilite;
	


	public Fantome(int x, int y, Branche branche, Personnage pm) {
		super(x, y, branche);

		this.pacMan =  pm;
		this.chemin = new ArrayList<Integer>();

		this.invulnerable();
		this.horsJeu();
		this.datePrison = 0;
	}

	public void decisionDirection() {
		if (this.estSurNoeud()) {
			// On se trouve sur un noeud
			this.prochaineDirection();
		} else if(this.getDirection()==Personnage.STATIQUE) {
			// On est statique sur une branche
			this.prochaineDirection();
		}
	}

	private void prochaineDirection() {
		if (!chemin.isEmpty()) {
			int destination = chemin.get(chemin.size()-1);

			this.direction(destination);

			chemin.remove(chemin.size()-1);
		}
	}

	// GETTEUR
	public int getPositionX() {
		if (this.estEnJeu()) {
			return super.getPositionX();
		} else {
			return getPositionPrisonX();
		}
	}
	public int getPositionY() {
		if (this.estEnJeu()) {
			return super.getPositionY();
		} else {
			return getPositionPrisonY();
		}
	}
	public Personnage getPacMan() {return pacMan;}
	
	// SETTEUR
	public void setChemin(ArrayList<Integer> chemin) {this.chemin = chemin;}

	// ABSTRACT
	public void mort() {
		this.reinitialisation();
		this.invulnerable();
		this.horsJeu();
	}
	
	public void victoire(int nbTour) {
		this.mort();
		this.datePrison = nbTour;
	}
	
	public int getDatePrison() {return datePrison;}
	public int getTourVulnerabilite () {return tourVulnerabilite;}
	public boolean estInvulnerable() {return invulnerable;}
	public boolean estVulnerable() {return !invulnerable;}
	
	public void invulnerable() {
		invulnerable = true;
	}
	
	public void vulnerable(int nb) {
		invulnerable = false;
		tourVulnerabilite = nb;
	}


	public abstract void trouverChemin();
	public abstract int getPositionPrisonX();
	public abstract int getPositionPrisonY();
	public abstract int getEntreeEnJeu();
}
