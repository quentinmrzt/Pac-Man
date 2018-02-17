package view;

/* ############################################################################
 * 
 * TestKeyListener.java : démonstration de la capture d'événements issus du
 *                        clavier.
 * 
 * Auteur : Christophe Jacquet, Supélec
 * 
 * Historique
 * 2006-12-19  Création
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
 * Programme de test des KeyListener : affiche une fenêtre où un JLabel
 * affiche les codes de touches appuyées et relâchées.
 */
public class TestKeyListener extends JFrame {
	private static final long serialVersionUID = -5222658361778310082L;

	private TestKeyListener() {
		// création de la fenêtre
		super("TestKeyListener");

		// ajout d'un seul composant dans cette fenêtre : un JLabel
		JLabel label = new JLabel("Pressez une touche...");
		add(label, BorderLayout.CENTER);

		// ajoute un écouteur d'événements personnalisé à la fenêtre
		addKeyListener(new TitreKeyListener(label));

		// réglage des dimensions de la fenêtre
		setPreferredSize(new Dimension(300, 100));
		pack();
	}

	public static void main(String[] args) {
		// construit une fenêtre de type TestKeyListener et l'affiche
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new TestKeyListener();
				frame.setVisible(true);
			}
		});
	}
}


/**
 * Implémente l'interface KeyListener (écouteur d'événements clavier) : 
 * lorsqu'une touche est appuyée ou relâchée, affiche le code de touche
 * correspondant dans un objet JLabel qui doit être fourni au constructeur.
 */
class TitreKeyListener implements KeyListener {
	private final JLabel label;

	public TitreKeyListener(JLabel label_) {
		label = label_;
	}

	public void keyPressed(KeyEvent e) {
		label.setText("Touche pressée : " + e.getKeyCode() + 
				" (" + e.getKeyChar() + ")");
	}

	public void keyReleased(KeyEvent e) {
		label.setText("Touche relâchée : " + e.getKeyCode() +
				" (" + e.getKeyChar() + ")");
	}

	public void keyTyped(KeyEvent e) {
		// on ne fait rien
	}
}