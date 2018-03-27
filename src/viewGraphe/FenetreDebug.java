package viewGraphe;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import model.Modelisation;

public class FenetreDebug extends JFrame implements Observer {
	private Modelisation modelisation;	

	public FenetreDebug(Modelisation model) {
		this.setSize(600, 600);
		this.setTitle("Debug");
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		modelisation = model;

		this.setVisible(true);
		this.requestFocus();
	}


	// Implementation de Observer
	public void update(Observable o, Object arg) {
		
	}
}