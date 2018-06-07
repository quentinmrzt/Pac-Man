package viewEvolution;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import evolution.Evolution;
import evolution.Information;
import graphe.Graphe;
import jeu.Jeu;
import jeu.LancerPacMan;
import model.Map;
import viewGraphique.Fenetre;
import viewGraphique.InformationGraphique;

public class Menu extends JMenuBar implements ActionListener{
	protected JMenu menu;
	protected JMenuItem jouer;
	protected JMenuItem quitter;

	protected JMenu mode;
	protected JMenuItem nuage;
	protected JMenuItem debug;
	
	private Evolution evolution;

	public Menu(Evolution evolution) {
		this.evolution = evolution;
		
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
		mode = new JMenu("Graphique");
		mode.setMnemonic(KeyEvent.VK_M);

		// Item pour lancer la suppresion de pixel
		nuage = new JMenuItem("Nuage de points");
		nuage.setMnemonic(KeyEvent.VK_B);
		nuage.setActionCommand("Nuage");
		nuage.addActionListener(this);

		// Item pour débuger
		debug = new JMenuItem("Vide");
		debug.setMnemonic(KeyEvent.VK_B);
		debug.setActionCommand("Vide");
		debug.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

			}
		});

		// Intégration des items au mode
		mode.add(nuage);
		mode.add(debug);

		// --------------------
		// Intégration des menus à la bar
		this.add(menu);
		this.add(mode);
	}

	public void actionPerformed(ActionEvent arg0) {
		List<Integer> valeurs = new ArrayList<Integer>();
		
		for (int generation=0 ; generation<evolution.getNombreGeneration() ; generation++) {
			int somme = 0;
			for (int individu=0 ; individu<evolution.getNombrePopulation() ; individu++) {
				somme += evolution.getInformation(generation, individu).getScore();
			}
			int moyenne = somme/evolution.getNombrePopulation();
			valeurs.add(moyenne);
		}

		InformationGraphique barres = new InformationGraphique(valeurs);
		new Fenetre(barres);
	}
}
