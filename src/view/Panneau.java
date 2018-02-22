package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;


public class Panneau extends JPanel implements Observer {
	protected ZoneDeJeu zdj;
	protected JLabel pacManTxt, gommeTxt, score;
	protected Controller controller;

	public Panneau(Controller control) {
		super();
		
		controller = control;
		
		// Ajout d'un écouteur sur le clavier
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new ControleClavier(control));

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
		zdj = new ZoneDeJeu(control);
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

		// Texte pour les gommes
		constraints.gridwidth = GridBagConstraints.REMAINDER; // le dernier de la ligne
		constraints.gridheight = 1;
		constraints.gridx = 1;
		constraints.gridy = 1;
		
		gommeTxt = new JLabel("Gomme: ");
		gommeTxt.setPreferredSize(new Dimension(400, 30)) ;
		this.add(gommeTxt, constraints);

		// Texte pour le score
		constraints.gridwidth = GridBagConstraints.REMAINDER; // le dernier de la ligne
		constraints.gridheight = GridBagConstraints.REMAINDER; // le dernier de la colonne
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START; // place toi en haut à gauche
		
		score = new JLabel("Score: ");
		score.setPreferredSize(new Dimension(400, 30)) ;
		this.add(score, constraints);		
	}

	public void update(Observable o, Object arg) {
		zdj.update(o, arg);
		
		pacManTxt.setText("Pac-Man: "+controller.getModel().getPM().toString());
		gommeTxt.setText("Gomme: "+controller.getModel().getMap().getNbGomme()+". Super gomme: "+controller.getModel().getMap().getNbSuperGomme()+".");
		score.setText("Score: "+controller.getModel().getScore()+".");
	}
}