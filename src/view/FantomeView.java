package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import model.Modelisation;
import model.Personnage;

public class FantomeView extends ElementDuJeu implements Observer{
	final int MONTER = 0 ;
	final int DESCENDRE = 1;

	private int direction;
	private boolean monter;
	private BufferedImage mouvement[][];

	public FantomeView(int x, int y, int fantome) {
		super(x, y);
		monter = true;
		direction = Personnage.HAUT;

		// On ouvre les sprites
		try {
			// Pour les directions des fantomes
			BufferedImage tmpMouvement = null;

			if (fantome==Modelisation.BLINKY) {
				tmpMouvement = ImageIO.read(new File("image/Blinky.png"));
			} else if (fantome==Modelisation.PINKY) {
				tmpMouvement = ImageIO.read(new File("image/Pinky.png"));
			} else if (fantome==Modelisation.INKY) {
				tmpMouvement = ImageIO.read(new File("image/Inky.png"));
			} else if (fantome==Modelisation.CLYDE) {
				tmpMouvement = ImageIO.read(new File("image/Clyde.png"));
			}

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
		System.out.println(tmp.getDirection());
		direction = tmp.getDirection();
		monter = !monter;
	}
}
