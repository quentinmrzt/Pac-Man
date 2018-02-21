package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyboardFocusManager;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;


public class Panneau extends JPanel implements Observer {
	protected ZoneDeJeu zdj;
	//protected JButton demarrer;
	protected JLabel pacManTxt, gommeTxt, score;
	protected Controller controller;
	protected boolean jouer;

	public Panneau(Controller control) {
		super();
		
		jouer = true;
		controller = control;
		
		// Ajout d'un écouteur sur le clavier
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new ControleClavier(control));

		/*demarrer = new JButton("P");
		demarrer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				jouer = !jouer;
				update(null, null);
			}
		});*/
		
		this.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		
		// Definition des contraintes pour la zone de jeu
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0; 
		constraints.gridy = 0; 
		constraints.gridwidth =  1;
		constraints.gridheight = GridBagConstraints.REMAINDER; // le dernier de la colonne

		// Zone de jeu
		zdj = new ZoneDeJeu(control);
		zdj.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // bordure en rouge
		this.add(zdj, constraints);

		// Bouton de pause
		/*constraints.fill = GridBagConstraints.BOTH; // occupe l'espace
		constraints.gridx = 0; // position x: 0
		constraints.gridy = 1; // position y: 1
		constraints.gridwidth = 1; 
		constraints.gridheight = 1;
		demarrer.setPreferredSize(new Dimension(45, 45)) ;
		this.add(demarrer, constraints);*/
		
		
		

		// Texte pour pacMan
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = GridBagConstraints.REMAINDER; // le dernier de la ligne
		constraints.gridheight = 1;
		constraints.gridx = 1;
		constraints.gridy = 0;

		pacManTxt = new JLabel("Pac-Man: ");
		pacManTxt.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		pacManTxt.setPreferredSize(new Dimension(400, 30)) ;
		this.add(pacManTxt, constraints);

		// Texte pour les gommes
		constraints.gridwidth = GridBagConstraints.REMAINDER; // le dernier de la ligne
		constraints.gridheight = 1;
		constraints.gridx = 1;
		constraints.gridy = 1;
		
		gommeTxt = new JLabel("Gomme: ");
		gommeTxt.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		gommeTxt.setPreferredSize(new Dimension(400, 30)) ;
		this.add(gommeTxt, constraints);

		// Texte pour le score
		constraints.gridwidth = GridBagConstraints.REMAINDER; // le dernier de la ligne
		constraints.gridheight = 1; // le dernier de la colonne
		constraints.gridx = 3;
		constraints.gridy = 3;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		
		score = new JLabel("Score: ");
		score.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
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