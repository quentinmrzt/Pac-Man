package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Case  {
	static int MUR=0;
	static int SOL=1;
	static int GOMME=2;
	static int SUPERGOMME=3;
	static int PRISON=9;

	protected int cote = 50 ;
	protected int type;
	protected int positionX ;
	protected int positionY ;
	
	BufferedImage image ;

	public Case(int x, int y, int cote, int type) {
		this.cote = cote;
		positionX = x;
		positionY = y;
		this.type = type;
		
		try {
			if (type==MUR) {
				image = ImageIO.read(new File("image/1.png"));
			} else if (type==SOL) {
				image = ImageIO.read(new File("image/2.png"));
			} else if (type==GOMME) {
				image = ImageIO.read(new File("image/3.png"));
			} else if (type==SUPERGOMME) {
				image = ImageIO.read(new File("image/4.png"));
			} else {
				image = ImageIO.read(new File("image/5.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
