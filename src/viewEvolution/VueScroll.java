package viewEvolution;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import evolution.Evolution;

public class VueScroll extends JPanel {
	private int largeur, hauteur;
	private VueInformation vueInformation;

	public VueScroll(Evolution evolution) {
		super();
		
		this.setLayout(new BorderLayout());
		
		//this.setLayout(new GridLayout(1, 1));
		
		this.vueInformation = new VueInformation(evolution);
		JScrollPane scroll = new JScrollPane(vueInformation, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(0, 0, 100, 100);
		
		this.largeur = 1150;
		this.hauteur = 450;
		this.add(scroll);
		this.setPreferredSize(new Dimension(largeur, hauteur));
		this.setMinimumSize(new Dimension(largeur, hauteur));
	}
}
