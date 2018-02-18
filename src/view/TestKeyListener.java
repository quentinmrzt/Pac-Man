package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import controller.Controller;

class TestKeyListener implements KeyListener {
	private Controller control;

	public TestKeyListener(Controller c) {
		control = c;
	}

	public void keyPressed(KeyEvent e) {
		//System.out.println("Touche pressée : " + e.getKeyCode() + " (" + e.getKeyChar() + ")");
		
		// Control du PacMan
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			System.out.println("Haut");
			control.toucheHaut();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			System.out.println("Droite");
			control.toucheDroite();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			System.out.println("Bas");
			control.toucheBas();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			System.out.println("Gauche");
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