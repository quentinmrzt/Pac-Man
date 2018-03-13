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

public class FantomeView extends ElementDuJeu implements Observer {
	private final int MONTER = 0 ;
	private final int DESCENDRE = 1;

	// Permet de garde en m�moir la direction pr�c�dente si STATIQUE
	private int direction;
	private boolean monter;
	private BufferedImage mouvement[][];

	private Fantome fantome;

	public FantomeView(int x, int y, int typeFantome, Fantome f) {
		super(f.getPositionX(),f.getPositionY());

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
			System.err.println("Il y a eu un probl�me dans l'initialisation des sprites de PacMan.");
		}
	}

	// GETTEUR
	public int getPositionPixelX() {
		return fantome.getPositionX()*COTE;
	}
	public int getPositionPixelY() {
		return fantome.getPositionY()*COTE;
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
		//Personnage tmp = (Personnage) obs;

		this.setPositionTabX(fantome.getPositionX());
		this.setPositionTabY(fantome.getPositionY());
		if(fantome.getDirection()!=Personnage.STATIQUE) {
			direction = fantome.getDirection();
		}

		monter = !monter;
	}
}
