package viewEvolution;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import graphe.Graphe;
import jeu.Jeu;
import jeu.LancerPacMan;
import model.Map;

public class Menu extends JMenuBar {
	protected JMenu menu;
	protected JMenuItem jouer;
	protected JMenuItem quitter;

	protected JMenu mode;
	protected JMenuItem suppression;
	protected JMenuItem debug;

	public Menu() {
		// --------------------
		// MENU
		menu = new JMenu("Menu") ;
		menu.setMnemonic(KeyEvent.VK_M);

		// Item pour quitter
		jouer = new JMenuItem("Jouer");
		jouer.setMnemonic(KeyEvent.VK_B);
		jouer.setActionCommand("Jouer");
		jouer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Map map = new Map("src/map_gomme.txt");
				Graphe graphe = new Graphe(map);

				new LancerPacMan(new Jeu(map,graphe));
			}
		});

		// Item pour quitter
		quitter = new JMenuItem("Quitter");
		quitter.setMnemonic(KeyEvent.VK_B);
		quitter.setActionCommand("Quitter");
		quitter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// Intégration des items au menu
		menu.add(jouer);
		menu.add(quitter);


		// --------------------
		// MODE
		mode = new JMenu("Vide");
		mode.setMnemonic(KeyEvent.VK_M);

		// Item pour lancer la suppresion de pixel
		suppression = new JMenuItem("Vide");
		suppression.setMnemonic(KeyEvent.VK_B);
		suppression.setActionCommand("Vide");
		suppression.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// On demande au controleur de supprimer des pixels

			}
		});

		// Item pour débuger
		debug = new JMenuItem("Vide");
		debug.setMnemonic(KeyEvent.VK_B);
		debug.setActionCommand("Vide");
		debug.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println("Vide");
			}
		});

		// Intégration des items au mode
		mode.add(suppression);
		mode.add(debug);

		// --------------------
		// Intégration des menus à la bar
		this.add(menu);
		this.add(mode);
	}
}
