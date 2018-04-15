package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Map;
import model.Monde;

public class ZoneDeJeu extends JPanel implements Observer {
	private Monde monde;
	private Case tabCases[][];
	private PacManView pacManView;
	private FantomeView blinky;
	private FantomeView pinky;
	private FantomeView inky;
	private FantomeView clyde;

	public ZoneDeJeu(Monde monde) {	
		this.monde = monde;

		// On récupére la map du modèle
		Map map = monde.getMap();
		pacManView = new PacManView(monde.getPM());

		blinky = new FantomeView(12,14,Monde.BLINKY, monde.getFantome(Monde.BLINKY));
		pinky = new FantomeView(13,14,Monde.PINKY, monde.getFantome(Monde.PINKY));
		inky = new FantomeView(14,14,Monde.INKY, monde.getFantome(Monde.INKY));
		clyde = new FantomeView(15,14,Monde.CLYDE, monde.getFantome(Monde.CLYDE));

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
				if(pacManView.memeEmplacement(x, y)) {
					g.drawImage(pacManView.getImage(), pacManView.getPositionPixelX(), pacManView.getPositionPixelY(), null);
				} else if(blinky.memeEmplacement(x, y)) {
					g.drawImage(blinky.getImage(), blinky.getPositionPixelX(), blinky.getPositionPixelY(), null);
				} else if(pinky.memeEmplacement(x, y)) {
					g.drawImage(pinky.getImage(), pinky.getPositionPixelX(), pinky.getPositionPixelY(), null);
				} else if(inky.memeEmplacement(x, y)) {
					g.drawImage(inky.getImage(), inky.getPositionPixelX(), inky.getPositionPixelY(), null);
				} else if(clyde.memeEmplacement(x, y)) {
					g.drawImage(clyde.getImage(), clyde.getPositionPixelX(), clyde.getPositionPixelY(), null);
				} else {
					g.drawImage(tabCases[x][y].getImage(), tabCases[x][y].getPositionPixelX(), tabCases[x][y].getPositionPixelY(), null);
				}
			}
		}
	}

	public void update(Observable obs, Object arg) {		
		blinky.update(obs,arg);
		pinky.update(obs,arg);
		inky.update(obs,arg);
		clyde.update(obs,arg);
		pacManView.update(obs, arg);

		Map tmp = monde.getMap();

		// On maj l'affichage du tableau
		for (int y=0 ; y<tmp.getTailleY() ; y++) {
			for (int x=0 ; x<tmp.getTailleX() ; x++) {
				tabCases[x][y].setImage(tmp.getCase(x, y));
			}
		}

		this.repaint();
	}
}
