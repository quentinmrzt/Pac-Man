package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Modelisation;


public class Panneau extends JPanel implements Observer {
	private Modelisation modelisation;
	protected ZoneDeJeu zdj;
	protected JLabel pacManTxt, BlinkyTxt, PinkyTxt, InkyTxt, ClydeTxt, gommeTxt, score;

	public Panneau(Modelisation model) {
		super();

		modelisation = model;

		// Ajout du layout GridBag
		this.setLayout(new GridBagLayout());

		// Contraintes
		GridBagConstraints constraints = new GridBagConstraints();

		// Definition des contraintes pour la zone de jeu
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0; 
		constraints.gridy = 0; 
		constraints.gridwidth =  1;
		constraints.gridheight = GridBagConstraints.REMAINDER; // le dernier de la colonne
		constraints.insets = new Insets(5, 5, 5, 5);

		// Zone de jeu
		zdj = new ZoneDeJeu(modelisation);
		//zdj.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		this.add(zdj, constraints);

		// Texte pour pacMan
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = GridBagConstraints.REMAINDER; // le dernier de la ligne
		constraints.gridheight = 1;
		constraints.gridx = 1;
		
		constraints.gridy = 0;
		pacManTxt = new JLabel("Pac-Man: ");
		pacManTxt.setPreferredSize(new Dimension(400, 30)) ;
		this.add(pacManTxt, constraints);
		
		constraints.gridy = 1;
		BlinkyTxt = new JLabel("Blinky: ");
		BlinkyTxt.setPreferredSize(new Dimension(400, 30)) ;
		this.add(BlinkyTxt, constraints);
		
		constraints.gridy = 2;
		PinkyTxt = new JLabel("Pinky: ");
		PinkyTxt.setPreferredSize(new Dimension(400, 30)) ;
		this.add(PinkyTxt, constraints);
		
		constraints.gridy = 3;
		InkyTxt = new JLabel("Inky: ");
		InkyTxt.setPreferredSize(new Dimension(400, 30)) ;
		this.add(InkyTxt, constraints);
		
		constraints.gridy = 4;
		ClydeTxt = new JLabel("Clyde: ");
		ClydeTxt.setPreferredSize(new Dimension(400, 30)) ;
		this.add(ClydeTxt, constraints);

		// Texte pour les gommes		
		constraints.gridy = 5;
		gommeTxt = new JLabel("Gomme: ");
		gommeTxt.setPreferredSize(new Dimension(400, 30)) ;
		this.add(gommeTxt, constraints);

		// Texte pour le score
		constraints.gridheight = GridBagConstraints.REMAINDER; // le dernier de la colonne
		constraints.gridy = 6;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START; // place toi en haut à gauche

		score = new JLabel("Score: ");	
		score.setPreferredSize(new Dimension(400, 30)) ;
		this.add(score, constraints);
	}

	public void update(Observable o, Object arg) {
		pacManTxt.setText("Pac-Man: "+modelisation.getPM().toString());
		BlinkyTxt.setText("Blinky: "+modelisation.getFantome(Modelisation.BLINKY).toString());
		PinkyTxt.setText("Pinky: "+modelisation.getFantome(Modelisation.PINKY).toString());
		InkyTxt.setText("Inky: "+modelisation.getFantome(Modelisation.INKY).toString());
		ClydeTxt.setText("Clyde: "+modelisation.getFantome(Modelisation.CLYDE).toString());
		
		if(o instanceof Modelisation) {
			gommeTxt.setText("Gomme: "+modelisation.getMap().getNbGomme()+". Super gomme: "+modelisation.getMap().getNbSuperGomme()+".");
			score.setText("Score: "+modelisation.getScore()+".");
		}

		zdj.update(o, arg);
	}
}