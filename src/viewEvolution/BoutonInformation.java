package viewEvolution;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import evolution.Information;
import viewArbre.Fenetre;

public class BoutonInformation extends JButton implements ActionListener {
	private Information information;
	private String texte;
	private int taille;
	
	public BoutonInformation(String str, Information information) {
		super(information.getScore()+"");
		this.texte = str;
		this.taille = 60;
		this.information = information;
		this.addActionListener(this);
		this.setPreferredSize(new Dimension(this.taille,this.taille));
		
		if(information.getScore()==148) {
			this.setBackground(Color.BLUE);
		} else if(information.estVainqueur()) {
			this.setBackground(Color.GREEN);
		} else if(information.estTournoi()) {
			this.setBackground(Color.ORANGE);
		}
	}
	
	public void actionPerformed(ActionEvent arg0) {
		new Fenetre(information);
	}
	
	public int getTaille() { return this.taille; }

}
