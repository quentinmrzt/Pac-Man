package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import controller.Controller;

class ControleClavier implements KeyListener {
	private Controller control;

	public ControleClavier(Controller c) {
		control = c;
	}

	public void keyPressed(KeyEvent e) {		
		// Control du PacMan
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			control.toucheHaut();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			control.toucheDroite();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			control.toucheBas();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			control.toucheGauche();
		}
	}

	public void keyReleased(KeyEvent e) {
		//System.out.println("Touche relâchée : " + e.getKeyCode() +" (" + e.getKeyChar() + ")");
	}

	public void keyTyped(KeyEvent e) {
		// on ne fait rien
	}
}