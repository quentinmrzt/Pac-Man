package viewEvolution;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import evolution.Evolution;


public class VueInformation extends JPanel {
	private Evolution evolution;
	private int largeur, hauteur;

	public VueInformation(Evolution evolution) {
		super();

		this.setBackground(Color.WHITE);

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(gb);

		gbc.fill = GridBagConstraints.BOTH;
		//gbc.weighty = 1;

		for (int generation=1 ; generation<=100 ; generation++) {
			JButton bouton = new JButton("Generation "+generation);
			bouton.setEnabled(false);
			gbc.weightx = 1;
			gbc.gridx = 0;
			gbc.gridy = generation;
			gbc.insets = new Insets(1, 2, 1, 2);
			gb.setConstraints(bouton, gbc);
			this.add(bouton);

			for (int individu=1 ; individu<=evolution.getNombrePopulation() ; individu++) {
				JButton boutonTmp = new BoutonInformation(individu+"", evolution.getInformation(generation-1,individu-1));
				gbc.weightx = 0;
				gbc.insets = new Insets(1, 0, 1, 0);
				gbc.gridx = individu;
				gb.setConstraints(boutonTmp, gbc);
				this.add(boutonTmp);
			}
		}
		
		this.largeur = 150+(60*100);
		this.hauteur = 62*100;

		this.setPreferredSize(new Dimension(largeur, hauteur));

		this.setVisible(true);
	}

	public void paint(Graphics g) {
		super.paint(g);
	}
}
