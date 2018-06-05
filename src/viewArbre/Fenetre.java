package viewArbre;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import arbre.Arbre;

public class Fenetre extends JFrame implements ActionListener {
	private Panneau panneau;
	//private JScrollPane scroll;


	public Fenetre(Arbre arbre) {
		this.setTitle("Arbre");
		
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setPreferredSize(new Dimension(1500,700));

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
		this.panneau = new Panneau(arbre);
		JScrollPane scroll = new JScrollPane(panneau);
		scroll.setBounds(0, 0, 1200, 600);
		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(1200, 600));
		contentPane.add(scroll);
		//this.setContentPane(contentPane);	
		this.add(contentPane, constraints);

		// Texte pour pacMan
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = GridBagConstraints.REMAINDER; // le dernier de la ligne
		constraints.gridheight = 1;
		constraints.gridx = 1;

		Button bouton = new Button("test");
		bouton.addActionListener(this);

		this.add(bouton,constraints);

		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("CLIQUE");
		panneau.tailleCercle -= 10;
	}
}