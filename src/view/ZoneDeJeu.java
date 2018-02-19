package view;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import controller.Controller;
import model.Map;
import model.Modelisation;

public class ZoneDeJeu extends JPanel implements Observer {

	Controller controller;
	Case tabCases[][];
	PacManView pacManView;

	public ZoneDeJeu(Controller c) {
		super();
		this.setPreferredSize(new Dimension(800, 800)) ;
		controller = c;
		
		// On récupére la map du modèle
		Map map = c.getMap();
		pacManView = new PacManView(c.getPM());

		// On fait un tableau de case contenant les images a afficher
		tabCases = new Case[map.getTailleX()][map.getTailleY()];

		for (int y=0 ; y<map.getTailleY() ; y++) {
			for (int x=0 ; x<map.getTailleX() ; x++) {
				tabCases[x][y] = new Case(x,y, map.getCase(x, y));
			}
		}

	}

	public int getTailleY() {
		return tabCases[0].length;
	}

	public int getTailleX() {
		return tabCases.length;
	}
	
	public void setImage(int x, int y) {
		//tabCases[x][y].
	}

	protected void paintComponent(Graphics g) {
		for (int y=0 ; y<getTailleY() ; y++) {
			for (int x=0 ; x<getTailleX() ; x++) {
				if(pacManView.getPositionTabX()==x && pacManView.getPositionTabY()==y) {
					g.drawImage(pacManView.getImage(), pacManView.getPositionX(), pacManView.getPositionY(), null);
				} else {
					g.drawImage(tabCases[x][y].getImage(), tabCases[x][y].getPositionX(), tabCases[x][y].getPositionY(), null);
				}
			}
		}
		System.out.println("Je rentre");
	}

	public void update(Observable o, Object arg) {
		if(o instanceof Modelisation){
			// On maj pacman
			pacManView.update(o, arg);
			
			System.out.println("Je suis dans la Zone de Jeu et la valeur a été modifié !");
			//repaint(); // Fait appel à paint(), maj la fenetre
		}
		
	}
}
