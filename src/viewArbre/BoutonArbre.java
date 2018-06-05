package viewArbre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import arbre.Arbre;

public class BoutonArbre extends JButton implements ActionListener {
	private Arbre arbre;
	private String texte;
	
	public BoutonArbre(String str, Arbre arbre) {
		super(str);
		this.texte = str;
		this.arbre = arbre;
		this.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		new Fenetre(arbre);
	}
}
