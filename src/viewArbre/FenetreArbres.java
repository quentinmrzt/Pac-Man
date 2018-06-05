package viewArbre;

import java.awt.Button;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import arbre.Arbre;

public class FenetreArbres extends JFrame {
	public FenetreArbres(List<Arbre> arbres) {
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Arbres");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		 this.setLayout(new GridLayout(10, 10));
		 
		 for(int i=0 ; i<100 ; i++) {
			 BoutonArbre tmp = new BoutonArbre(i+"", arbres.get(i));
			 this.getContentPane().add(tmp);
		 }

		this.setVisible(true);
		this.requestFocus();
	}
}
