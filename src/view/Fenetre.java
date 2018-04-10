package view;

import java.awt.KeyboardFocusManager;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import controller.Controller;
import jeu.ControleClavier;
import model.Modelisation;

public class Fenetre extends JFrame implements Observer {
	private Modelisation modelisation;
	private Menu menu;
	private Panneau panneau;
	

	public Fenetre(Controller controller, Modelisation model) {
		this.setSize(900, 600);
		this.setTitle("Pac-Man");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		modelisation = model;

		// Ajout d'un écouteur sur le clavier
        addKeyListener(new ControleClavier(controller));
        // Je demande à ce que ce soit ma fenetre qui intercepte les touches du clavier
         
        requestFocusInWindow();
		//KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new ControleClavier(controller));

		// Panneau
		panneau = new Panneau(modelisation);
		this.add(panneau);

		// Menu
		menu = new Menu(modelisation);
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