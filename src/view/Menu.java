package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.Modelisation;
import viewGraphe.FenetreDebug;

public class Menu extends JMenuBar {
	private JMenu menu;
	private JMenuItem debug;
	private JMenuItem quitter;
	
	public Menu(Modelisation modelisation) {				
		// --------------------
		// MENU
		menu = new JMenu("Menu") ;
		menu.setMnemonic(KeyEvent.VK_M);

		
		// Item pour choisir une image
		debug = new JMenuItem("Débuger");
		debug.setMnemonic(KeyEvent.VK_B);
		debug.setActionCommand("Débuger");
		debug.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//app.maj();
				modelisation.addObserver(new FenetreDebug(modelisation));
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
		menu.add(debug);
		menu.add(quitter);

		
		// --------------------
		// Intégration des menus à la bar
		this.add(menu);
	}
}
