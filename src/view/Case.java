package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Case extends ElementDuJeu{
	final int MUR=0;
	final int SOL=1;
	final int GOMME=2;
	final int SUPERGOMME=3;
	final int PRISON=9;
	
	// On ouvre qu'une fois les images et elle ne change pas
	final static BufferedImage IMG_MUR = setImage("image/1.png");
	final static BufferedImage IMG_SOL = setImage("image/2.png");
	final static BufferedImage IMG_GOMME = setImage("image/3.png");
	final static BufferedImage IMG_SUPERGOMME = setImage("image/4.png");

	private int type;
	private BufferedImage image ; // l'image courante de la case

	public Case(int x, int y, int t) {
		super(x,y);
		type = t;
		setImage(type);
	}

	public BufferedImage getImage() {
		return image;
	}

	private static BufferedImage setImage(String chemin) {
		BufferedImage tmp = null;
		try {
			tmp = ImageIO.read(new File(chemin));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tmp;
	}

	public void setImage(int type) {
		if (type==MUR) {
			image = IMG_MUR;
		} else if (type==SOL || type==PRISON) {
			image = IMG_SOL;
		} else if (type==GOMME) {
			image = IMG_GOMME;
		} else if (type==SUPERGOMME) {
			image = IMG_SUPERGOMME;
		}
	}

}
