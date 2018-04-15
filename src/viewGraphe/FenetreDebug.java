package viewGraphe;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import model.Monde;

public class FenetreDebug extends JFrame implements Observer {
	private VueGraphe vueGraphe;
	
	public FenetreDebug(Monde monde) {
		this.setSize(500, 550);
		this.setTitle("Debug");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		// La vue du graphe
		vueGraphe = new VueGraphe(monde);
		this.add(vueGraphe);

		this.setVisible(true);
		this.requestFocus();
	}


	// Implementation de Observer
	public void update(Observable o, Object arg) {
		this.repaint();
	}
}