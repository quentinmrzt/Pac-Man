package arbre;

import java.util.ArrayList;

import model.Fantome;
import model.Modelisation;
import model.PacMan;
import model.Personnage;

public class TestEnnemi extends Test{
	private int direction;

	public TestEnnemi(Noeud test, Modelisation modelisation, int direction) {
		super(test,modelisation);
		this.direction = direction;
	}
	
	public String toString() {
		return "Test ennemi "+Personnage.afficheDirection(direction);
	}

	public boolean test() {
		PacMan pacMan = this.getModelisation().getPM();
		ArrayList<Fantome> fantomes = this.getModelisation().getFantome();

		if (pacMan.getNoeud()!=null) {
			// Pac man est sur un noeud
			for (Fantome f: fantomes) {
				if(f.getBranche().equals(pacMan.getNoeud().getBrancheDirection(direction))) {
					return true;
				}
			}
		} else {
			// Pac man est sur une branche
			for (Fantome f: fantomes) {
				if(pacMan.getBranche().equals(f.getBranche())) {
					// et un fantome se trouve sur sa branche
					if(direction==Personnage.HAUT && pacMan.getPositionY()>f.getPositionY()) {
						return true;
					} else if(direction==Personnage.DROITE && pacMan.getPositionX()<f.getPositionX()) {
						return true;
					} else if(direction==Personnage.BAS && pacMan.getPositionY()<f.getPositionY()) {
						return true;
					} else if(direction==Personnage.GAUCHE && pacMan.getPositionX()>f.getPositionX()) {
						return true;
					} else if(direction==Personnage.STATIQUE) {
						return true;
					}
				}
			}
		}

		return false;
	}
}
