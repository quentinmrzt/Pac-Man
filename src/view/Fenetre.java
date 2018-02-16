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
	    this.setSize(1000, 1200);
	    this.setTitle("Pac-Man");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    
	    this.controler = c;

		// Panneau
		panneau = new Panneau(this.controler);
		this.add(panneau);
	    
	    // Menu
	    menu = new Menu(controler);
		this.setJMenuBar(menu);
		
	    //pack();
	    this.setVisible(true);
	}

	// Observer
	public void update(Observable o, Object arg) {
		// Si c'est Modelisation qui m'a appel�
		if(o instanceof Modelisation){
			System.out.println("Je suis dans la Fenetre et la valeur a �t� modifi�: "+arg.toString());
			
			// Si le param�tre modifi� est un entier
			if (arg instanceof Integer) {
				System.out.println("Je suis un entier en param�tre.");
			}
			
			// Si le param�tre modifi� est une chaine de caract�re
			if(arg instanceof String) {
				System.out.println("Je suis une chaine de caract�re en param�tre.");
			}
		}
	}

	// GETTEUR
	public Panneau getPanneau() {
		return panneau;
	}
}