package view;


import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import controller.Controller;
import model.Map;
import model.Modelisation;
import model.PacMan;

public class ZoneDeJeu extends JPanel implements Observer {

	Case tabCases[][];
	PacManView pacManView;

	public ZoneDeJeu(Controller c) {
		super();
		this.setPreferredSize(new Dimension(800, 800)) ;

		// On récupére la map du modèle
		Map map = c.getMap();
		PacMan pc = c.getPM();
		pacManView = new PacManView(pc);

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

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int y=0 ; y<getTailleY() ; y++) {
			for (int x=0 ; x<getTailleX() ; x++) {
				if(pacManView.getPositionTabX()==x && pacManView.getPositionTabY()==y) {
					g.drawImage(pacManView.getImage(), pacManView.getPositionX(), pacManView.getPositionY(), null);
				} else {
					g.drawImage(tabCases[x][y].getImage(), tabCases[x][y].getPositionX(), tabCases[x][y].getPositionY(), null);
				}
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Modelisation){
			System.out.println("Je suis dans la Zone de Jeu et la valeur a été modifié !");
			
		}
		
	}
}
