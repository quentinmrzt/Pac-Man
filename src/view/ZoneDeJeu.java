package view;


import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Blinky;
import model.Clyde;
import model.Inky;
import model.Map;
import model.Modelisation;
import model.PacMan;
import model.Pinky;

public class ZoneDeJeu extends JPanel implements Observer {
	private Modelisation modelisation;
	private Case tabCases[][];
	private PacManView pacManView;
	private FantomeView blinky;
	private FantomeView pinky;
	private FantomeView inky;
	private FantomeView clyde;

	public ZoneDeJeu(Modelisation model) {	
		modelisation = model;
		
		// On récupére la map du modèle
		Map map = modelisation.getMap();
		pacManView = new PacManView(modelisation.getPM());

		blinky = new FantomeView(12,14,Modelisation.BLINKY);
		pinky = new FantomeView(13,14,Modelisation.PINKY);
		inky = new FantomeView(14,14,Modelisation.INKY);
		clyde = new FantomeView(15,14,Modelisation.CLYDE);

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
				} else if(blinky.getPositionTabX()==x && blinky.getPositionTabY()==y) {
					g.drawImage(blinky.getImage(), blinky.getPositionX(), blinky.getPositionY(), null);
				} else if(pinky.getPositionTabX()==x && pinky.getPositionTabY()==y) {
					g.drawImage(pinky.getImage(), pinky.getPositionX(), pinky.getPositionY(), null);
				} else if(inky.getPositionTabX()==x && inky.getPositionTabY()==y) {
					g.drawImage(inky.getImage(), inky.getPositionX(), inky.getPositionY(), null);
				} else if(clyde.getPositionTabX()==x && clyde.getPositionTabY()==y) {
					g.drawImage(clyde.getImage(), clyde.getPositionX(), clyde.getPositionY(), null);
				} else {
					g.drawImage(tabCases[x][y].getImage(), tabCases[x][y].getPositionX(), tabCases[x][y].getPositionY(), null);
				}
			}
		}
	}

	public void update(Observable obs, Object arg) {
		if(obs instanceof Blinky) {
			blinky.update(obs,arg);
		}

		if(obs instanceof Pinky) {
			pinky.update(obs,arg);
		}

		if(obs instanceof Inky) {
			inky.update(obs,arg);
		}

		if(obs instanceof Clyde) {
			clyde.update(obs,arg);
		}

		if(obs instanceof PacMan) {
			pacManView.update(obs, arg);
		}

		if(obs instanceof Map) {
			Map tmp = (Map) obs;

			// On maj l'affichage du tableau
			for (int y=0 ; y<tmp.getTailleY() ; y++) {
				for (int x=0 ; x<tmp.getTailleX() ; x++) {
					tabCases[x][y].setImage(tmp.getCase(x, y));
				}
			}
		}
	}
}
