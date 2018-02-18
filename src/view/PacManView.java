package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.PacMan;

public class PacManView extends ElementDuJeu {
	final int HAUT = 0;
	final int DROITE = 1;
	final int BAS = 2;
	final int GAUCHE = 3;
	
	final int OUVERT = 0 ;
	final int FERME = 1;
	
	private int direction = 0;
	private boolean boucheOuverte;
	private BufferedImage mouvement[][];
	private BufferedImage mort[];

	public PacManView(PacMan pm) {
		super(pm.getPositionX(), pm.getPositionY());
		direction = pm.getDirection();
		boucheOuverte = true;

		// On ouvre les sprites
		try {
			// Pour les directions de PacMan
			BufferedImage tmpMouvement = ImageIO.read(new File("image/PacManDirection.png"));
			mouvement = new BufferedImage[4][2];
			mouvement[HAUT][OUVERT] = tmpMouvement.getSubimage(0, COTE*0, COTE, COTE);
			mouvement[HAUT][FERME] = tmpMouvement.getSubimage(COTE, COTE*0, COTE, COTE);
			
			mouvement[DROITE][OUVERT] = tmpMouvement.getSubimage(0, COTE*1, COTE, COTE);
			mouvement[DROITE][FERME] = tmpMouvement.getSubimage(COTE, COTE*1, COTE, COTE);
			
			mouvement[BAS][OUVERT] = tmpMouvement.getSubimage(0, COTE*2, COTE, COTE);
			mouvement[BAS][FERME] = tmpMouvement.getSubimage(COTE, COTE*2, COTE, COTE);
			
			mouvement[GAUCHE][OUVERT] = tmpMouvement.getSubimage(0, COTE*3, COTE, COTE);
			mouvement[GAUCHE][FERME] = tmpMouvement.getSubimage(COTE, COTE*3, COTE, COTE);

			// Pour la mort de PacMan
			BufferedImage tmpMort = ImageIO.read(new File("image/PacManMort.png"));
			mort = new BufferedImage[12];
			for (int i=0 ; i<12 ; i++) {
				mort[i] = tmpMort.getSubimage(COTE*i, 0, COTE, COTE);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Il y a eu un problème dans l'initialisation des sprites de PacMan.");
			e.printStackTrace();
		}
	}

	// GETTEUR
	public BufferedImage getImage() {
		if (boucheOuverte) {
			return mouvement[direction][OUVERT];
		} else {
			return mouvement[direction][FERME];
		}
	}
}