package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Map;


public class Case extends ElementDuJeu{
	// On ouvre qu'une fois les images et elle ne change pas
	final static BufferedImage IMG_MUR = setImage("image/1.png");
	final static BufferedImage IMG_SOL = setImage("image/2.png");
	final static BufferedImage IMG_GOMME = setImage("image/3.png");
	final static BufferedImage IMG_SUPERGOMME = setImage("image/4.png");

	private int type;

	public Case(int x, int y, int t) {
		super(x,y);
		type = t;
	}

	// GETTEUR
	public BufferedImage getImage() {
		if (type==Map.MUR) {
			return IMG_MUR;
		} else if (type==Map.SOL || type==Map.PRISON) {
			return IMG_SOL;
		} else if (type==Map.GOMME) {
			return IMG_GOMME;
		} else if (type==Map.SUPERGOMME) {
			return IMG_SUPERGOMME;
		} else {
			System.out.println("ERREUR: Un type d'image demandé n'existe pas.");
			return IMG_MUR;
		}
	}

	// SETTEUR
	public void setImage(int t) {
		type = t;
	}

	private static BufferedImage setImage(String chemin) {
		BufferedImage tmp = null;
		try {
			tmp = ImageIO.read(new File(chemin));
		} catch (IOException e) {
			System.err.println("ERREUR: L'image n'a pas pu être chargée.");
			e.printStackTrace();
		}

		return tmp;
	}
}
