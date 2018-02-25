package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import model.PacMan;
import model.Personnage;

public class PacManView extends ElementDuJeu implements Observer {
	final int OUVERT = 0 ;
	final int FERME = 1;

	private int direction;
	private boolean boucheOuverte;
	private BufferedImage mouvement[][];
	private BufferedImage mort[];

	public PacManView(PacMan pm) {
		super(pm.getPositionX(), pm.getPositionY());
		PacMan pacMan = pm;
		direction = pacMan.getDirection();
		boucheOuverte = true;

		// On ouvre les sprites
		try {
			// Pour les directions de PacMan
			BufferedImage tmpMouvement = ImageIO.read(new File("image/PacManDirection.png"));
			mouvement = new BufferedImage[4][2];

			// SPRITE PM HAUT
			mouvement[Personnage.HAUT][OUVERT] = tmpMouvement.getSubimage(0, COTE*0, COTE, COTE);
			mouvement[Personnage.HAUT][FERME] = tmpMouvement.getSubimage(COTE, COTE*0, COTE, COTE);
			// SPRITE PM DROITE
			mouvement[Personnage.DROITE][OUVERT] = tmpMouvement.getSubimage(0, COTE*1, COTE, COTE);
			mouvement[Personnage.DROITE][FERME] = tmpMouvement.getSubimage(COTE, COTE*1, COTE, COTE);
			// SPRITE PM BAS
			mouvement[Personnage.BAS][OUVERT] = tmpMouvement.getSubimage(0, COTE*2, COTE, COTE);
			mouvement[Personnage.BAS][FERME] = tmpMouvement.getSubimage(COTE, COTE*2, COTE, COTE);
			// SPRITE PM GAUCHE
			mouvement[Personnage.GAUCHE][OUVERT] = tmpMouvement.getSubimage(0, COTE*3, COTE, COTE);
			mouvement[Personnage.GAUCHE][FERME] = tmpMouvement.getSubimage(COTE, COTE*3, COTE, COTE);

			// Pour la mort de PacMan
			BufferedImage tmpMort = ImageIO.read(new File("image/PacManMort.png"));
			mort = new BufferedImage[12];
			for (int i=0 ; i<12 ; i++) {
				mort[i] = tmpMort.getSubimage(COTE*i, 0, COTE, COTE);
			}
		} catch (IOException e) {
			System.err.println("Il y a eu un problème dans l'initialisation des sprites de PacMan.");
		}
	}

	// GETTEUR
	public BufferedImage getImage() {
		if (direction == Personnage.STATIQUE) {
			return mort[0];
		}

		if (boucheOuverte) {
			return mouvement[direction][OUVERT];
		} else {
			return mouvement[direction][FERME];
		}
	}

	// OBSERVER
	public void update(Observable obs, Object arg) {
		if(obs instanceof PacMan) {
			PacMan tmp = (PacMan) obs;
			
			this.setPositionTabX(tmp.getPositionX());
			this.setPositionTabY(tmp.getPositionY());
			direction = tmp.getDirection();
			boucheOuverte = !boucheOuverte;
		}
	}	
}