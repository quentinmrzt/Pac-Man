package view;


import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import controller.Controller;
import model.Map;
import model.PacMan;

public class ZoneDeJeu extends JPanel implements Observer {
	//private Controller controller;
	private Case tabCases[][];
	private PacManView pacManView;
	private FantomeView blinky;
	private FantomeView pinky;
	private FantomeView inky;
	private FantomeView clyde;

	public ZoneDeJeu(Controller c) {		
		// On récupére la map du modèle
		Map map = c.getModel().getMap();
		pacManView = new PacManView(c.getModel().getPM());
		
		blinky = new FantomeView(1,1);
		pinky = new FantomeView(1,1);
		inky = new FantomeView(1,1);
		clyde = new FantomeView(1,1);
		
		// On fait un tableau de case contenant les images a afficher
		tabCases = new Case[map.getTailleX()][map.getTailleY()];
		
		for (int y=0 ; y<map.getTailleY() ; y++) {
			for (int x=0 ; x<map.getTailleX() ; x++) {
				tabCases[x][y] = new Case(x,y, map.getCase(x, y));
			}
		}
		
		// La taille du panel fait exactement la taille de la map
		this.setPreferredSize(new Dimension(map.getTailleX()*16	, map.getTailleY()*16)) ;
		
	}

	public int getTailleY() {
		return tabCases[0].length;
	}

	public int getTailleX() {
		return tabCases.length;
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
		System.out.println("MAJ ZDJ");
		blinky.update(o,arg);
		pinky.update(o,arg);
		inky.update(o,arg);
		clyde.update(o,arg);
		
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
