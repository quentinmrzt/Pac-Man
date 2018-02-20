package view;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

import controller.Controller;

class ControleClavier implements KeyEventDispatcher {
	private Controller control;

	public ControleClavier(Controller c) {
		control = c;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			control.toucheHaut();
			System.out.println("Haut");
			break;
		case KeyEvent.VK_RIGHT:
			control.toucheDroite();
			System.out.println("Droite");
			break;
		case KeyEvent.VK_DOWN:
			control.toucheBas();
			System.out.println("Bas");
			break;
		case KeyEvent.VK_LEFT:
			control.toucheGauche();
			System.out.println("Gauche");
			break;
		}
		
		return false;
	}

}