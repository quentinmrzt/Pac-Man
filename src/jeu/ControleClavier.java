package jeu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import controller.Controller;

public class ControleClavier implements KeyListener  {
	private Controller control;

	public ControleClavier(Controller c) {
		control = c;
	}

	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
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
			
		case KeyEvent.VK_T:
			control.toucheT();
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		case KeyEvent.VK_PAGE_UP:
			control.touchePageUp();
			break;
		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}