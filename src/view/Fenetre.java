package view;

import javax.swing.JFrame;

import controller.Controller;
import observer.Observer;


public class Fenetre extends JFrame implements Observer {
	protected Controller controler;
	
	public Fenetre(Controller c) {
	    this.setSize(800, 450);
	    this.setTitle("Pac-Man");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    
	    this.controler = c;

	    
	    //pack();
	    this.setVisible(true);
	}

	// Observer
	public void update(String str) {

		// sert a reconstruire les composants au sein d'un layoutmanager en cas de modification "majeure"
		this.validate();
	}
}