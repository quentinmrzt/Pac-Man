package viewArbre;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import evolution.Individu;
import evolution.Information;
import graphe.Graphe;
import jeu.LancerPacMan;
import model.Map;

public class Fenetre extends JFrame implements ActionListener {
	private Panneau panneau;
	//private JScrollPane scroll;
	private Information information;
	private int largeur, hauteur;

	public Fenetre(Information information) {
		this.setTitle("Information");
		
		this.largeur = 1200;
		this.hauteur = 600;

		this.information = information;

		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setPreferredSize(new Dimension(largeur,hauteur));

		// Ajout du layout GridBag
		this.setLayout(new GridBagLayout());
		// Contraintes
		GridBagConstraints constraints = new GridBagConstraints();

		// Definition des contraintes pour 
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0; 
		constraints.gridy = 0; 
		constraints.gridwidth =  1;
		constraints.gridheight = GridBagConstraints.REMAINDER; // le dernier de la colonne
		//constraints.insets = new Insets(5, 5, 5, 5);

		// Panneau
		this.panneau = new Panneau(information.getArbre());
		JScrollPane scroll = new JScrollPane(panneau);
		scroll.setBounds(0, 0, 1100, 500);
		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(1100, 500));
		contentPane.add(scroll);
		//this.setContentPane(contentPane);
		this.add(contentPane, constraints);

		// Texte pour pacMan
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = GridBagConstraints.REMAINDER; // le dernier de la ligne
		constraints.gridheight = 1;
		constraints.gridx = 1;

		Button bouton = new Button("JOUER");
		bouton.addActionListener(this);

		this.add(bouton,constraints);

		// On centre la fenetre
		Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int positionX = (int) (bounds.getWidth()/2 - this.largeur/2);
		int positionY = (int) (bounds.getHeight()/2 - this.hauteur/2);
		this.setLocation(positionX, positionY);

		this.pack();
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		Map map = new Map("src/map_gomme.txt");
		Graphe graphe = new Graphe(map);

		Individu individu = new Individu(map, graphe, information.getArbre(), 80);

		new LancerPacMan(individu);
	}
}