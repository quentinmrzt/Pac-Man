package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import controller.Controller;
import model.Modelisation;


public class Fenetre extends JFrame implements Observer {
	private Controller controler;
	private Menu menu;
	private Panneau panneau;

	public Fenetre(Controller c) {
		this.setSize(800, 800);
		this.setTitle("Pac-Man");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		this.controler = c;

		// Panneau
		panneau = new Panneau(this.controler);
		this.add(panneau);
		

		// Menu
		menu = new Menu();
		this.setJMenuBar(menu);

		// ajoute un écouteur d'événements personnalisé à la fenêtre
		addKeyListener(new TestKeyListener(this.controler));

		//pack();
		this.setVisible(true);
	}

	// Observer
	public void update(Observable o, Object arg) {
		// Si c'est Modelisation qui m'a appelé
		if(o instanceof Modelisation){
			System.out.println("Je suis dans la Fenetre et la valeur a été modifié: "+arg.toString());

			// Si le paramètre modifié est un entier
			if (arg instanceof Integer) {
				System.out.println("Je suis un entier en paramètre.");
			}

			// Si le paramètre modifié est une chaine de caractère
			if(arg instanceof String) {
				System.out.println("Je suis une chaine de caractère en paramètre.");
			}
		}
	}

	// GETTEUR
	public Panneau getPanneau() {
		return panneau;
	}
}