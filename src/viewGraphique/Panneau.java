package viewGraphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class Panneau extends JPanel implements Observer {
	private InformationGraphique information;
	private int largeur, hauteur;
	
	public Panneau(InformationGraphique information) {
		this.largeur = 900;
		this.hauteur = 600;
		
		this.setPreferredSize(new Dimension(largeur, hauteur));

		this.information = information;
	}

	public int getHauteur() { return this.hauteur; }
	public int getLargeur() { return this.largeur; }
	
	public void paint(Graphics g) {
		super.paint(g);

		Color c = g.getColor();

		int espace = 0;
		
		int posMinX = 10;
		int posMaxX = 850;
		//int largeurMin = 0;
		int largeurMax = posMaxX - posMinX;
		
		int posMinY = 10;
		int posMaxY = 560;
		int hauteurMin = 0;
		int hauteurMax = posMaxY - posMinY;

		g.drawLine(posMinX, posMinY, posMinX, posMaxY);
		g.drawLine(posMinX, posMaxY, posMaxX, posMaxY);

		// normalisée = (originale - MIN) * (max - min) / (MAX - MIN) + min 
		// [MIN,MAX] : interval d'origine 
		// [min,max] : interval cible 

		int width = (largeurMax-(espace*information.getNombre()))/information.getNombre();
		for (int i=0 ; i<information.getNombre() ; i++) {
			int height = (information.getValeur(i) - information.getMin()) * (hauteurMax - hauteurMin) / (information.getMax() - information.getMin()) + hauteurMin; 

			int x = posMinX + (i*width) + (i*espace);
			int y = posMaxY-height;

			g.fillRect(x, y, width, height);
		}

		g.setColor(c);
	}

	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
}
