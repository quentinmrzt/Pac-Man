package viewEvolution;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import evolution.Evolution;

public class VueBarre extends JPanel {
	private int largeur, hauteur;
	private String strGeneration, strPopulation, strParticipant, strVainqueur, strProfondeur, strMutation;
	
	public VueBarre(Evolution evolution) {
		// DONNEES
		this.setGeneration(evolution.getNombreGeneration());
		this.setPopulation(evolution.getNombrePopulation());
		this.setParticipant(evolution.getNombreSelection());
		this.setVainqueur(evolution.getNombreVainqueur());
		this.setProfondeur(evolution.getProfondeur());
		this.setMutation(evolution.getPourcentageMutation());
		
		// GRAPHISME
		this.setBackground(Color.WHITE);
		
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(gb);
		
		// LABEL
		JLabel labelGeneration = new JLabel("NB GENERATION: "+this.getGeneration());
		labelGeneration.setFont(new Font(null, Font.BOLD, 12));
		labelGeneration.setHorizontalAlignment(SwingConstants.TRAILING);
		gbc.insets = new Insets(0, 5, 0, 5);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gb.setConstraints(labelGeneration, gbc);
		
		JLabel labelPopulation = new JLabel("NB POPULATION: "+this.getPopulation());
		labelPopulation.setHorizontalAlignment(SwingConstants.TRAILING);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gb.setConstraints(labelPopulation, gbc);
		
		JLabel labelParticipant = new JLabel("PARTICIPANT: "+this.getParticipant());
		labelParticipant.setHorizontalAlignment(SwingConstants.TRAILING);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gb.setConstraints(labelParticipant, gbc);
		
		JLabel labelVainqueur = new JLabel("VAINQUEUR: "+this.getVainqueur());
		labelVainqueur.setHorizontalAlignment(SwingConstants.TRAILING);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 1;
		gb.setConstraints(labelVainqueur, gbc);
		
		JLabel labelProfondeur = new JLabel("PROFONDEUR: "+this.getProfondeur());
		labelProfondeur.setHorizontalAlignment(SwingConstants.TRAILING);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 2;
		gbc.gridy = 0;
		gb.setConstraints(labelProfondeur, gbc);
		
		JLabel labelMutation = new JLabel("% MUTATION: "+this.getMutation());
		labelMutation.setHorizontalAlignment(SwingConstants.TRAILING);
		gbc.gridx = 2;
		gbc.gridy = 1;
		gb.setConstraints(labelMutation, gbc);
		
		this.add(labelGeneration);
		this.add(labelPopulation);
		this.add(labelParticipant);
		this.add(labelVainqueur);
		this.add(labelProfondeur);
		this.add(labelMutation);
		
		// BOUTON
		int tailleBouton = 40;
		JButton boutonLancer = new JButton(new ImageIcon("image\\lancer.png"));
		boutonLancer.setPreferredSize(new Dimension(tailleBouton,tailleBouton));
		boutonLancer.setEnabled(false);
		gbc.insets = new Insets(0, 1, 0, 1);
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		gb.setConstraints(boutonLancer, gbc);
		
		JButton boutonArreter = new JButton(new ImageIcon("image\\arreter.png"));
		boutonArreter.setPreferredSize(new Dimension(tailleBouton,tailleBouton));
		boutonArreter.setEnabled(false);
		gbc.weightx = 0;
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		gb.setConstraints(boutonArreter, gbc);
		
		JButton boutonSuivant = new JButton(new ImageIcon("image\\suivant.png"));
		boutonSuivant.setPreferredSize(new Dimension(tailleBouton,tailleBouton));
		boutonSuivant.setEnabled(false);
		gbc.gridx = 7;
		gbc.gridy = 0;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		gb.setConstraints(boutonSuivant, gbc);
		
		this.add(boutonLancer);
		this.add(boutonArreter);
		this.add(boutonSuivant);
	}

	public int getLargeur() { return this.largeur; }
	public int getHauteur() { return this.hauteur; }

	public void setGeneration(int i) { this.strGeneration = this.getNombreBonneTaille(i,3); }
	public void setPopulation(int i) { this.strPopulation = this.getNombreBonneTaille(i,3); }
	public void setParticipant(int i) { this.strParticipant = this.getNombreBonneTaille(i,3); }
	public void setVainqueur(int i) { this.strVainqueur = this.getNombreBonneTaille(i,3); }
	public void setProfondeur(int i) { this.strProfondeur = this.getNombreBonneTaille(i,3); }
	public void setMutation(int i) { this.strMutation = this.getNombreBonneTaille(i,3); }
	
	public String getGeneration() { return this.strGeneration; }
	public String getPopulation() { return this.strPopulation; }
	public String getParticipant() { return this.strParticipant; }
	public String getVainqueur() { return this.strVainqueur; }
	public String getProfondeur() { return this.strProfondeur; }
	public String getMutation() { return this.strMutation; }
	
	public String getNombreBonneTaille(int nombre, int tailleFinale) {
		String strNombre = nombre+"";
		int taille = strNombre.length();
		
		String zero = "";
		for (int i=taille ; i<tailleFinale ; i++) {
			zero += "0";
		}
		
		return zero+strNombre;
	}
}
