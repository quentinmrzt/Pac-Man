package jeu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Monde;
import model.Personnage;

public class ControleClavier implements KeyListener  {
	private Monde monde;
	
	public ControleClavier(Monde monde) {
		this.monde = monde;
	}
		
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			this.toucheHaut();
			break;
		case KeyEvent.VK_RIGHT:
			this.toucheDroite();
			break;
		case KeyEvent.VK_DOWN:
			this.toucheBas();
			break;
		case KeyEvent.VK_LEFT:
			this.toucheGauche();
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		case KeyEvent.VK_PAGE_UP:
			this.touchePageUp();
			break;
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	// CLAVIER
	public void toucheHaut() {
		monde.directionPersonnage(Personnage.HAUT, Monde.PACMAN);
	}
	public void toucheDroite() {
		monde.directionPersonnage(Personnage.DROITE, Monde.PACMAN);
	}
	public void toucheBas() {
		monde.directionPersonnage(Personnage.BAS, Monde.PACMAN);
	}
	public void toucheGauche() {
		monde.directionPersonnage(Personnage.GAUCHE, Monde.PACMAN);
	}
	public void touchePageUp() {
		monde.tourDeJeu();
	}
}