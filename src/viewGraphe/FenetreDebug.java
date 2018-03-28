package viewGraphe;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import model.Modelisation;

public class FenetreDebug extends JFrame implements Observer {
	private VueGraphe vueGraphe;
	
	public FenetreDebug(Modelisation modelisation) {
		this.setSize(600, 600);
		this.setTitle("Debug");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		// La vue du graphe
		vueGraphe = new VueGraphe(modelisation);
		this.add(vueGraphe);

		this.setVisible(true);
		this.requestFocus();
	}


	// Implementation de Observer
	public void update(Observable o, Object arg) {
		System.out.println(arg);
		this.repaint();
	}
}