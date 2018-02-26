package view;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

import controller.Controller;

class ControleClavier implements KeyEventDispatcher {
	private Controller control;

	public ControleClavier(Controller c) {
		control = c;
	}

	public boolean dispatchKeyEvent(KeyEvent e) {
		switch(e.getKeyCode()) {
		// PACMAN
		case KeyEvent.VK_UP:
			control.toucheHaut();
			break;
		case KeyEvent.VK_RIGHT:
			control.toucheDroite();
			break;
		case KeyEvent.VK_DOWN:
			control.toucheBas();
			break;
		case KeyEvent.VK_LEFT:
			control.toucheGauche();
			break;
		// BLINKY
		case KeyEvent.VK_I:
			control.toucheHautBlinky();
			break;
		case KeyEvent.VK_L:
			control.toucheDroiteBlinky();
			break;
		case KeyEvent.VK_K:
			control.toucheBasBlinky();
			break;
		case KeyEvent.VK_J:
			control.toucheGaucheBlinky();
			break;
		// AUTRE
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
		}

		return false;
	}
}