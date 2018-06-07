package viewEvolution;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;

import javax.swing.JFrame;

import evolution.Evolution;

public class FenetreEvolution extends JFrame {
	//private Information[][] informations;
	private int largeur, hauteur;

	public FenetreEvolution(Evolution evolution) {
		this.largeur = 1200;
		this.hauteur = 600;

		this.setPreferredSize(new Dimension(largeur, hauteur));
		this.setTitle("Pac-Man Genetique");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		// MENU
		this.setJMenuBar(new Menu(evolution));

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(gb);

		// Panneau
		VueBarre barre = new VueBarre(evolution);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(barre, gbc);

		VueGeneration generation = new VueGeneration(evolution);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(generation, gbc);

		VuePied pied = new VuePied();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(pied, gbc);

		this.add(barre);
		this.add(generation);
		this.add(pied);

		
		// On centre la fenetre
		Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int positionX = (int) (bounds.getWidth()/2 - this.largeur/2);
		int positionY = (int) (bounds.getHeight()/2 - this.hauteur/2);
		this.setLocation(positionX, positionY);

		pack();
		this.setVisible(true);
	}
}
