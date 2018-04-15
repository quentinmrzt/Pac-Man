package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import controller.Controller;
import jeu.ControleClavier;
import model.Monde;

public class Fenetre extends JFrame implements Observer {
	private Menu menu;
	private Panneau panneau;

	public Fenetre(Controller controller, Monde monde) {
		this.setSize(900, 600);
		this.setTitle("Pac-Man");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		// Ajout d'un écouteur sur le clavier
        addKeyListener(new ControleClavier(controller));
        // Je demande à ce que ce soit ma fenetre qui intercepte les touches du clavier
         
        requestFocusInWindow();
		//KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new ControleClavier(controller));

		// Panneau
		this.panneau = new Panneau(monde);
		this.add(panneau);

		// Menu
		menu = new Menu(monde);
		this.setJMenuBar(menu);

		//pack();
		this.setVisible(true);
		this.requestFocus();
	}

	// GETTEUR
	public Panneau getPanneau() {
		return panneau;
	}

	// Implementation de Observer
	public void update(Observable o, Object arg) {
		panneau.update(o,arg);
	}
}