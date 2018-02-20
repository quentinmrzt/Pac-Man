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
	//private Controller controller;
	private Case tabCases[][];
	private PacManView pacManView;

	public ZoneDeJeu(Controller c) {
		//super();
		this.setPreferredSize(new Dimension(600	, 600)) ;
		//controller = c;

		// On récupére la map du modèle
		Map map = c.getModel().getMap();
		pacManView = new PacManView(c.getModel().getPM());

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
	}

	public void update(Observable o, Object arg) {
		if(o instanceof Modelisation) {
			// On ne fait rien pour le moment
		}

		if(o instanceof PacMan) {
			pacManView.update(o, arg);
		}

		if(o instanceof Map) {
			Map tmp = (Map) o;
			
			// On maj l'affichage du tableau
			for (int y=0 ; y<tmp.getTailleY() ; y++) {
				for (int x=0 ; x<tmp.getTailleX() ; x++) {
					tabCases[x][y].setImage(tmp.getCase(x, y));
				}
			}
		}
	}
}
