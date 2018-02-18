package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Case extends ElementDuJeu{
	static int MUR=0;
	static int SOL=1;
	static int GOMME=2;
	static int SUPERGOMME=3;
	static int PRISON=9;

	protected int type;


	private BufferedImage image ;

	public Case(int x, int y, int t) {
		super(x,y);
		type = t;

		if (type==MUR) {
			setImage("image/1.png");
		} else if (type==SOL || type==PRISON) {
			setImage("image/2.png");
		} else if (type==GOMME) {
			setImage("image/3.png");
		} else if (type==SUPERGOMME) {
			setImage("image/4.png");
		} else {
			setImage("image/5.png");
		}
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(String chemin) {
		try {
			image = ImageIO.read(new File(chemin));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
