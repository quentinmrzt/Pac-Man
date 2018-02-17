package view;

/* ############################################################################
 * 
 * TestKeyListener.java : d�monstration de la capture d'�v�nements issus du
 *                        clavier.
 * 
 * Auteur : Christophe Jacquet, Sup�lec
 * 
 * Historique
 * 2006-12-19  Cr�ation
 * 
 * ############################################################################
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


/**
 * Programme de test des KeyListener : affiche une fen�tre o� un JLabel
 * affiche les codes de touches appuy�es et rel�ch�es.
 */
public class TestKeyListener extends JFrame {
	private static final long serialVersionUID = -5222658361778310082L;

	private TestKeyListener() {
		// cr�ation de la fen�tre
		super("TestKeyListener");

		// ajout d'un seul composant dans cette fen�tre : un JLabel
		JLabel label = new JLabel("Pressez une touche...");
		add(label, BorderLayout.CENTER);

		// ajoute un �couteur d'�v�nements personnalis� � la fen�tre
		addKeyListener(new TitreKeyListener(label));

		// r�glage des dimensions de la fen�tre
		setPreferredSize(new Dimension(300, 100));
		pack();
	}

	public static void main(String[] args) {
		// construit une fen�tre de type TestKeyListener et l'affiche
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new TestKeyListener();
				frame.setVisible(true);
			}
		});
	}
}


/**
 * Impl�mente l'interface KeyListener (�couteur d'�v�nements clavier) : 
 * lorsqu'une touche est appuy�e ou rel�ch�e, affiche le code de touche
 * correspondant dans un objet JLabel qui doit �tre fourni au constructeur.
 */
class TitreKeyListener implements KeyListener {
	private final JLabel label;

	public TitreKeyListener(JLabel label_) {
		label = label_;
	}

	public void keyPressed(KeyEvent e) {
		label.setText("Touche press�e : " + e.getKeyCode() + 
				" (" + e.getKeyChar() + ")");
	}

	public void keyReleased(KeyEvent e) {
		label.setText("Touche rel�ch�e : " + e.getKeyCode() +
				" (" + e.getKeyChar() + ")");
	}

	public void keyTyped(KeyEvent e) {
		// on ne fait rien
	}
}