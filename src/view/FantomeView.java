package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import model.Fantome;
import model.Modelisation;
import model.Personnage;

public class FantomeView extends ElementDuJeu implements Observer{
	private final int MONTER = 0 ;
	private final int DESCENDRE = 1;

	private int direction;
	private boolean monter;
	private BufferedImage mouvement[][];
	
	private Fantome fantome;
	private int positionPrisonX;
	private int positionPrisonY;

	public FantomeView(int x, int y, int typeFantome, Fantome f) {
		super(x,y);
		positionPrisonX = x;
		positionPrisonY = y;
		fantome = f;

		// On ouvre les sprites
		try {
			// Pour les directions des fantomes
			BufferedImage tmpMouvement = null;

			if (typeFantome==Modelisation.BLINKY) {
				tmpMouvement = ImageIO.read(new File("image/Blinky.png"));
			} else if (typeFantome==Modelisation.PINKY) {
				tmpMouvement = ImageIO.read(new File("image/Pinky.png"));
			} else if (typeFantome==Modelisation.INKY) {
				tmpMouvement = ImageIO.read(new File("image/Inky.png"));
			} else if (typeFantome==Modelisation.CLYDE) {
				tmpMouvement = ImageIO.read(new File("image/Clyde.png"));
			}
			monter = true;
			direction = Personnage.HAUT;

			mouvement = new BufferedImage[4][2];

			for (int i=0 ; i<4 ; i++) {
				mouvement[i][MONTER] = tmpMouvement.getSubimage(COTE*(i*2), 0, COTE, COTE);
				mouvement[i][DESCENDRE] = tmpMouvement.getSubimage(COTE*((i*2)+1), 0, COTE, COTE);
			}
		} catch (IOException e) {
			System.err.println("Il y a eu un problème dans l'initialisation des sprites de PacMan.");
		}
	}

	// GETTEUR
	public int getPositionPixelX() {
		if (enPrison()) {
			return positionPrisonX*COTE;
		} else {
			return getPositionPixelX();
		}
	}
	public int getPositionPixelY() {
		if (enPrison()) {
			return positionPrisonY*COTE;
		} else {
			return getPositionPixelY();
		}
	}
	
	public boolean enPrison() {
		if(fantome.estEnJeu()) {
			return false;
		} else {
			return true;
		}
	}
	public BufferedImage getImage() {
		if (monter) {
			return mouvement[direction][MONTER];
		} else {
			return mouvement[direction][DESCENDRE];
		}
	}

	public void update(Observable obs, Object arg) {
		Personnage tmp = (Personnage) obs;

		this.setPositionTabX(tmp.getPositionX());
		this.setPositionTabY(tmp.getPositionY());
		if(tmp.getDirection()!=Personnage.STATIQUE) {
			direction = tmp.getDirection();
		}
		monter = !monter;
	}
}
