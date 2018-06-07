package viewGraphique;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;


public class Fenetre extends JFrame {
	private VueNuageDePoint panneau;

	public Fenetre(InformationGraphique information) {
		this.setSize(new Dimension(1200, 700));
		this.setTitle("Graphique");
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

	    this.setLayout(new FlowLayout());

		
		// Panneau
		this.panneau = new VueNuageDePoint(information);
		this.add(panneau);

		pack();
		this.setVisible(true);
	}
}
